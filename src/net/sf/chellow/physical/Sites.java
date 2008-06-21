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

package net.sf.chellow.physical;

import java.util.List;

import net.sf.chellow.monad.DeployerException;
import net.sf.chellow.monad.DesignerException;
import net.sf.chellow.monad.Hiber;
import net.sf.chellow.monad.Invocation;
import net.sf.chellow.monad.MonadUtils;
import net.sf.chellow.monad.ProgrammerException;
import net.sf.chellow.monad.Urlable;
import net.sf.chellow.monad.UserException;

import net.sf.chellow.monad.types.MonadLong;
import net.sf.chellow.monad.types.MonadUri;
import net.sf.chellow.monad.types.UriPathElement;

import org.hibernate.HibernateException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Sites implements Urlable {
	//public static final SiteNameDataSource DEFAULT_SOURCE = new DefaultSiteName();
	public static final UriPathElement URI_ID;
	//public static final Map<Long, SiteNameDataSource> sources = new HashMap<Long, SiteNameDataSource>();
	static {
		try {
			URI_ID = new UriPathElement("sites");
		} catch (UserException e) {
			throw new RuntimeException(e);
		} catch (ProgrammerException e) {
			throw new RuntimeException(e);		}
	}

	static public Site getSite(MonadLong id) throws ProgrammerException,
			UserException {
		try {
			Site site = (Site) Hiber.session().get(Site.class, id.getLong());
			if (site == null) {
				throw UserException
						.newOk("There is no site with " + "that id.");
			}
			return site;
		} catch (HibernateException e) {
			throw new ProgrammerException(e);
		}
	}

	private Organization organization;

	Sites(Organization organization) {
		setOrganization(organization);
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public MonadUri getUri() throws ProgrammerException, UserException {
		return organization.getUri().resolve(getUriId()).append("/");
	}

	public void httpPost(Invocation inv) throws ProgrammerException,
			UserException, DesignerException, DeployerException {
		Document doc = MonadUtils.newSourceDocument();
		SiteCode code = inv.getValidatable(SiteCode.class, "code");
		String name = inv.getString("name");
		if (!inv.isValid()) {
			throw UserException.newOk(doc, null);
		}
		Site site = organization.insertSite(code, name);
		Hiber.commit();
		inv.sendCreated(site.getUri());
	}

	public void httpGet(Invocation inv) throws DesignerException,
			ProgrammerException, UserException, DeployerException {
		Document doc = MonadUtils.newSourceDocument();
		Element source = (Element) doc.getFirstChild();
		Element organizationSource = (Element) source.appendChild(organization
				.toXML(doc));
		List<Site> sites = null;
		if (inv.hasParameter("search-pattern")) {
			String searchTerm = inv.getString("search-pattern");
			if (!inv.isValid()) {
				throw UserException.newOk(doc, null);
			}
			sites = organization.findSites(searchTerm);
		} else {
			sites = organization.findSites();
		}
		for (Site site : sites) {
			organizationSource.appendChild(site.toXML(doc));
		}
		inv.sendOk(doc);
	}

	public UriPathElement getUriId() {
		return URI_ID;
	}

	public Site getChild(UriPathElement uriId) throws UserException,
			ProgrammerException {
		Site site = (Site) Hiber
				.session()
				.createQuery(
						"from Site site where site.organization = :organization and site.id = :siteId")
				.setEntity("organization", organization).setLong("siteId",
						Long.parseLong(uriId.getString())).uniqueResult();
		if (site == null) {
			throw UserException.newNotFound();
		}
		return site;
	}

	public void httpDelete(Invocation inv) throws ProgrammerException,
			UserException {
		// TODO Auto-generated method stub

	}

	public Site getSite(SiteCode code) throws UserException,
			ProgrammerException {
		Site site = (Site) Hiber
				.session()
				.createQuery(
						"from Site site where site.organization = :organization and site.code.string = :siteCode")
				.setEntity("organization", organization).setString("siteCode",
						code.getString()).uniqueResult();
		if (site == null) {
			throw UserException.newNotFound();
		}
		return site;
	}
	/*
	public SiteNameDataSource getSiteNameDataSource() throws ProgrammerException, UserException {
		// find properties file
		if (ChellowProperties.propertiesExists(getUri().append("names/"), "names.properties")) {
			ChellowProperties props = new ChellowProperties(getUri().append("names/"), "names.properties");
			long propsLastModified = props.getLastModified();
			SiteNameDataSource source = sources.get(organization.getId());
			if (source == null || source.getValidFrom() < propsLastModified) {
				sources.put(organization.getId(), new CSVSiteName(organization.getId(), propsLastModified));
			}
		}
		SiteNameDataSource source = sources.get(organization.getId());
		if (source == null) {
			source = DEFAULT_SOURCE;
		}
		return source;
	}
	*/
}