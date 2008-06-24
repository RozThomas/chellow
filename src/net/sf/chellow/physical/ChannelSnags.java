package net.sf.chellow.physical;

import java.util.List;

import net.sf.chellow.billing.DceService;
import net.sf.chellow.monad.Hiber;
import net.sf.chellow.monad.HttpException;
import net.sf.chellow.monad.InternalException;
import net.sf.chellow.monad.Invocation;
import net.sf.chellow.monad.MonadUtils;
import net.sf.chellow.monad.Urlable;
import net.sf.chellow.monad.XmlDescriber;
import net.sf.chellow.monad.XmlTree;
import net.sf.chellow.monad.types.MonadDate;
import net.sf.chellow.monad.types.MonadUri;
import net.sf.chellow.monad.types.UriPathElement;

import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ChannelSnags implements Urlable, XmlDescriber {
	static private final int PAGE_SIZE = 20;

	public static final UriPathElement URI_ID;

	static {
		try {
			URI_ID = new UriPathElement("channel-snags");
		} catch (HttpException e) {
			throw new RuntimeException(e);
		}
	}

	DceService dceService;

	public ChannelSnags(DceService dceService) {
		this.dceService = dceService;
	}

	public UriPathElement getUriId() {
		return URI_ID;
	}

	public MonadUri getUri() throws InternalException, HttpException {
		return dceService.getUri().resolve(getUriId()).append("/");
	}

	@SuppressWarnings("unchecked")
	public void httpGet(Invocation inv) throws HttpException {
		Document doc = MonadUtils.newSourceDocument();
		Element source = doc.getDocumentElement();
		Element snagsElement = (Element) toXml(doc);
		source.appendChild(snagsElement);
		snagsElement.appendChild(dceService.toXml(doc, new XmlTree("provider",
						new XmlTree("organization"))));
		List<ChannelSnag> snagsChannel = (List<ChannelSnag>) Hiber
				.session()
				.createQuery(
						"from ChannelSnag snag where snag.dateResolved is null and snag.service = :service order by snag.channel.supply.id, snag.channel.isImport, snag.channel.isKwh, snag.description, snag.startDate.date")
				.setEntity("service", dceService).setMaxResults(PAGE_SIZE)
				.list();
		for (ChannelSnag snag : snagsChannel) {
			snagsElement.appendChild(snag.toXml(doc, new XmlTree("channel",
							new XmlTree("supply"))));
		}
		source.appendChild(MonadDate.getMonthsXml(doc));
		source.appendChild(MonadDate.getDaysXml(doc));
		source.appendChild(new MonadDate().toXml(doc));
		inv.sendOk(doc);
	}

	@SuppressWarnings("unchecked")
	public void httpPost(Invocation inv) throws HttpException {
		if (inv.hasParameter("ignore")) {
			MonadDate ignoreDate = inv.getMonadDate("ignore-date");
			ScrollableResults snags = Hiber
					.session()
					.createQuery(
							"from ChannelSnag snag where dceService = :dceService and snag.finishDate < :ignoreDate")
					.setEntity("dceService", dceService).setTimestamp(
							"ignoreDate", ignoreDate.getDate()).scroll(
							ScrollMode.FORWARD_ONLY);
			while (snags.next()) {
				ChannelSnag snag = (ChannelSnag) snags.get(0);
				snag.resolve(true);
				Hiber.session().flush();
				Hiber.session().clear();
			}
			Hiber.commit();
			inv.sendSeeOther(getUri());
		}
	}

	public Urlable getChild(UriPathElement urlId) throws HttpException {
		return Snag.getSnag(urlId.getString());
	}

	public void httpDelete(Invocation inv) throws HttpException {
		// TODO Auto-generated method stub
	}

	public MonadUri getMonadUri() throws InternalException {
		// TODO Auto-generated method stub
		return null;
	}

	public Node toXml(Document doc) throws InternalException, HttpException {
		return doc.createElement("snags-channel");
	}

	public Node toXml(Document doc, XmlTree tree) throws HttpException {
		// TODO Auto-generated method stub
		return null;
	}

}