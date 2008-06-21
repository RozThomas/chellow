/*
 
 Copyright 2005 Meniscus Systems Ltd
 
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

import net.sf.chellow.monad.DeployerException;
import net.sf.chellow.monad.DesignerException;
import net.sf.chellow.monad.Hiber;
import net.sf.chellow.monad.Invocation;
import net.sf.chellow.monad.MonadUtils;
import net.sf.chellow.monad.ProgrammerException;
import net.sf.chellow.monad.Urlable;
import net.sf.chellow.monad.UserException;
import net.sf.chellow.monad.XmlDescriber;
import net.sf.chellow.monad.XmlTree;
import net.sf.chellow.monad.types.MonadUri;
import net.sf.chellow.monad.types.UriPathElement;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

@SuppressWarnings("serial")
public class Invoices implements Urlable, XmlDescriber {
	public static final UriPathElement URI_ID;

	static {
		try {
			URI_ID = new UriPathElement("invoices");
		} catch (UserException e) {
			throw new RuntimeException(e);
		} catch (ProgrammerException e) {
			throw new RuntimeException(e);
		}
	}

	private Batch batch;

	public Invoices(Batch batch) {
		setBatch(batch);
	}

	public Batch getBatch() {
		return batch;
	}

	void setBatch(Batch batch) {
		this.batch = batch;
	}

	public UriPathElement getUrlId() {
		return URI_ID;
	}

	public MonadUri getUri() throws ProgrammerException, UserException {
		return batch.getUri().resolve(getUrlId()).append("/");
	}

	public void httpPost(Invocation inv) throws ProgrammerException,
			UserException, DesignerException, DeployerException {
	}

	public void httpGet(Invocation inv) throws DesignerException,
			ProgrammerException, UserException, DeployerException {
		inv.sendOk(document());
	}

	@SuppressWarnings("unchecked")
	private Document document() throws ProgrammerException, UserException,
			DesignerException {
		Document doc = MonadUtils.newSourceDocument();
		Element source = doc.getDocumentElement();
		Element invoicesElement = toXML(doc);
		source.appendChild(invoicesElement);
		invoicesElement.appendChild(batch.getXML(new XmlTree("service",
				new XmlTree("provider", new XmlTree("organization"))), doc));
		for (Invoice invoice : (List<Invoice>) Hiber
				.session()
				.createQuery(
						"from Invoice invoice where invoice.batch = :batch order by invoice.bill.account.reference")
				.setEntity("batch", batch).list()) {
			invoicesElement.appendChild(invoice.getXML(new XmlTree("bill",
					new XmlTree("account")), doc));
		}
		return doc;
	}

	public Invoice getChild(UriPathElement uriId) throws UserException,
			ProgrammerException {
		Invoice invoice = (Invoice) Hiber
				.session()
				.createQuery(
						"from Invoice invoice where invoice.batch = :batch and invoice.id = :invoiceId")
				.setEntity("batch", batch).setLong("invoiceId",
						Long.parseLong(uriId.getString())).uniqueResult();
		if (invoice == null) {
			throw UserException.newNotFound();
		}
		return invoice;
	}

	public void httpDelete(Invocation inv) throws ProgrammerException,
			UserException {
	}

	public Element toXML(Document doc) throws ProgrammerException,
			UserException {
		Element billsElement = doc.createElement("invoices");
		return billsElement;
	}

	public Node getXML(XmlTree tree, Document doc) throws ProgrammerException,
			UserException {
		return null;
	}
}