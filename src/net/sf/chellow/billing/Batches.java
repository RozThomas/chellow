/*
 
 Copyright 2005-2008 Meniscus Systems Ltd
 
 This file is part of Chellow.

 Chellow is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 Chellow is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with Chellow; if not, write to the Free Software
 Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

 */

package net.sf.chellow.billing;

import java.util.List;

import net.sf.chellow.monad.Hiber;
import net.sf.chellow.monad.Invocation;
import net.sf.chellow.monad.MethodNotAllowedException;
import net.sf.chellow.monad.MonadUtils;
import net.sf.chellow.monad.NotFoundException;
import net.sf.chellow.monad.Urlable;
import net.sf.chellow.monad.HttpException;
import net.sf.chellow.monad.UserException;
import net.sf.chellow.monad.XmlDescriber;
import net.sf.chellow.monad.XmlTree;
import net.sf.chellow.monad.types.MonadUri;
import net.sf.chellow.monad.types.UriPathElement;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

@SuppressWarnings("serial")
public class Batches implements Urlable, XmlDescriber {
	public static final UriPathElement URI_ID;

	static {
		try {
			URI_ID = new UriPathElement("batches");
		} catch (HttpException e) {
			throw new RuntimeException(e);
		}
	}

	private Contract contract;

	public Batches(Contract contract) {
		setContract(contract);
	}

	public Contract getContract() {
		return contract;
	}

	void setContract(Contract contract) {
		this.contract = contract;
	}

	public UriPathElement getUrlId() {
		return URI_ID;
	}

	public MonadUri getUri() throws HttpException {
		return contract.getUri().resolve(getUrlId()).append("/");
	}

	public void httpPost(Invocation inv) throws HttpException {
		String reference = inv.getString("reference");
		if (!inv.isValid()) {
			throw new UserException(document());
		}
		Batch batch = contract.insertBatch(reference);
		Hiber.commit();
		inv.sendCreated(document(), batch.getUri());
	}

	public void httpGet(Invocation inv) throws HttpException {
		inv.sendOk(document());
	}

	public Batch getChild(UriPathElement uriId) throws HttpException {
		Batch batch = (Batch) Hiber
				.session()
				.createQuery(
						"from Batch batch where batch.service = :service and batch.id = :batchId")
				.setEntity("service", contract).setLong("batchId",
						Long.parseLong(uriId.getString())).uniqueResult();
		if (batch == null) {
			throw new NotFoundException();
		}
		return batch;
	}

	public void httpDelete(Invocation inv) throws HttpException {
		throw new MethodNotAllowedException();
	}

	public Node toXml(Document doc) throws HttpException {
		Element batchesElement = doc.createElement("batches");
		return batchesElement;
	}

	public Node toXml(Document doc, XmlTree tree) throws HttpException {
		return null;
	}

	@SuppressWarnings("unchecked")
	private Document document() throws HttpException {
		Document doc = MonadUtils.newSourceDocument();
		Element source = doc.getDocumentElement();
		Element batchesElement = (Element) toXml(doc);
		source.appendChild(batchesElement);
		batchesElement.appendChild(contract.toXml(doc, new XmlTree("provider")
				.put("organization")));
		for (Batch batch : (List<Batch>) Hiber
				.session()
				.createQuery(
						"from Batch batch where batch.contract = :contract order by batch.reference")
				.setEntity("contract", contract).list()) {
			batchesElement.appendChild(batch.toXml(doc));
		}
		return doc;
	}
}