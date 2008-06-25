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

import java.util.Set;

import net.sf.chellow.billing.Account;
import net.sf.chellow.billing.HhdcContract;
import net.sf.chellow.billing.DcsService;
import net.sf.chellow.billing.MopService;
import net.sf.chellow.billing.SupplierService;
import net.sf.chellow.data08.MpanRaw;
import net.sf.chellow.monad.DeployerException;
import net.sf.chellow.monad.DesignerException;
import net.sf.chellow.monad.Hiber;
import net.sf.chellow.monad.Invocation;
import net.sf.chellow.monad.InternalException;
import net.sf.chellow.monad.Urlable;
import net.sf.chellow.monad.HttpException;
import net.sf.chellow.monad.UserException;

import net.sf.chellow.monad.types.MonadBoolean;
import net.sf.chellow.monad.types.MonadInteger;
import net.sf.chellow.monad.types.MonadUri;
import net.sf.chellow.monad.types.UriPathElement;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Mpan extends PersistentEntity {
	static public Mpan getMpan(Long id) throws InternalException,
			HttpException {
		Mpan mpan = (Mpan) Hiber.session().get(Mpan.class, id);
		if (mpan == null) {
			throw new UserException
					("There is no mpan with that id.");
		}
		return mpan;
	}

	private SupplyGeneration supplyGeneration;

	private MpanTop mpanTop;

	private MpanCore mpanCore;

	private HhdcContract dceService;

	private Account dceAccount;

	private SupplierService supplierService;

	private Account supplierAccount;

	private MopService mopService;

	private Account mopAccount;

	private DcsService dcsService;

	private Account dcsAccount;

	private boolean hasImportKwh;

	private boolean hasImportKvarh;

	private boolean hasExportKwh;

	private boolean hasExportKvarh;

	private int agreedSupplyCapacity;

	Mpan() {
	}

	Mpan(SupplyGeneration supplyGeneration, MpanTop mpanTop,
			MpanCore mpanCore, HhdcContract dceService, Account supplierAccount,
			SupplierService supplierService, boolean hasImportKwh,
			boolean hasImportKvarh, boolean hasExportKwh,
			boolean hasExportKvarh, int agreedSupplyCapacity)
			throws InternalException, HttpException {
		this();
		this.supplyGeneration = supplyGeneration;
		update(mpanTop, mpanCore,
				dceService, supplierAccount, supplierService, hasImportKwh,
				hasImportKvarh, hasExportKwh, hasExportKvarh,
				agreedSupplyCapacity);
	}

	public SupplyGeneration getSupplyGeneration() {
		return supplyGeneration;
	}

	protected void setSupplyGeneration(SupplyGeneration supplyGeneration) {
		this.supplyGeneration = supplyGeneration;
	}

	public MpanTop getMpanTop() {
		return mpanTop;
	}

	void setMpanTop(MpanTop mpanTop) {
		this.mpanTop = mpanTop;
	}

	public MpanCore getMpanCore() {
		return mpanCore;
	}

	void setMpanCore(MpanCore mpanCore) {
		this.mpanCore = mpanCore;
	}

	public MopService getMopService() {
		return mopService;
	}

	void setMopService(MopService mopService) {
		this.mopService = mopService;
	}

	public Account getMopAccount() {
		return mopAccount;
	}

	void setMopAccount(Account mopAccount) {
		this.mopAccount = mopAccount;
	}

	public HhdcContract getDceService() {
		return dceService;
	}

	void setDceService(HhdcContract dceService) {
		this.dceService = dceService;
	}

	public Account getDceAccount() {
		return dceAccount;
	}

	void setDceAccount(Account dceAccount) {
		this.dceAccount = dceAccount;
	}

	public DcsService getDcsService() {
		return dcsService;
	}

	void setDcsService(DcsService dcsService) {
		this.dcsService = dcsService;
	}

	public Account getDcsAccount() {
		return dcsAccount;
	}

	void setDcsAccount(Account dcsAccount) {
		this.dcsAccount = dcsAccount;
	}

	public SupplierService getSupplierService() {
		return supplierService;
	}

	void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	public Account getSupplierAccount() {
		return supplierAccount;
	}

	void setSupplierAccount(Account supplierAccount) {
		this.supplierAccount = supplierAccount;
	}

	public boolean getHasImportKwh() {
		return hasImportKwh;
	}

	protected void setHasImportKwh(boolean hasImportKwh) {
		this.hasImportKwh = hasImportKwh;
	}

	public boolean getHasImportKvarh() {
		return hasImportKvarh;
	}

	protected void setHasImportKvarh(boolean hasImportKvarh) {
		this.hasImportKvarh = hasImportKvarh;
	}

	public boolean getHasExportKwh() {
		return hasExportKwh;
	}

	protected void setHasExportKwh(boolean hasExportKwh) {
		this.hasExportKwh = hasExportKwh;
	}

	public boolean getHasExportKvarh() {
		return hasExportKvarh;
	}

	protected void setHasExportKvarh(boolean hasExportKvarh) {
		this.hasExportKvarh = hasExportKvarh;
	}

	public int getAgreedSupplyCapacity() {
		return agreedSupplyCapacity;
	}

	protected void setAgreedSupplyCapacity(int agreedSupplyCapacity) {
		this.agreedSupplyCapacity = agreedSupplyCapacity;
	}

	void update(MpanTop mpanTop, MpanCore mpanCore,
			HhdcContract dceService, Account supplierAccount,
			SupplierService supplierService, boolean hasImportKwh,
			boolean hasImportKvarh, boolean hasExportKwh,
			boolean hasExportKvarh, int agreedSupplyCapacity)
			throws InternalException, HttpException {
		if (!mpanTop.getDso().equals(mpanCore.getDso())) {
			throw new UserException
					("The MPAN top line DSO doesn't match the MPAN core DSO.");
		}
		if (getMpanTop() != null
				&& !getMpanTop().getLlf().getIsImport() ==
						mpanTop.getLlf().getIsImport()) {
			throw new UserException
					("You can't change an import mpan into an export one, and vice versa.");
		}
		if (dceService == null) {
			hasImportKwh = false;
			hasImportKvarh = false;
			hasExportKwh = false;
			hasExportKvarh = false;
		}
		/*
		Ssc kwRegister = (Ssc) Hiber
				.session()
				.createQuery(
						"from Register register where register.meterTimeswitch = :mtc and register.units = :units")
				.setEntity("mtc", meterTimeswitch).setInteger("units",
						Ssc.Units.KW.ordinal()).uniqueResult();
		int pc = Integer.parseInt(profileClass.getCode().getString());
		if (pc > 4 && kwRegister == null) {
			throw UserException
					.newInvalidParameter("For a profile class of 05 and above, the meter timeswitch must have a kW register.");
		}
		if (pc < 5 && kwRegister != null) {
			throw UserException
					.newInvalidParameter("For a profile class of 04 and below, the meter timeswitch cannot have a kW register.");
		}
		if (pc == 0 & meterTimeswitch.getRegisters().size() > 0) {
			throw UserException
					.newInvalidParameter("For a profile class of 00, the meter timeswitch cannot have any registers.");
		}
		*/
		setMpanTop(mpanTop);
		if (mpanCore == null) {
			throw new InternalException("The mpan core can't be null.");
		}
		setMpanCore(mpanCore);
		if (dceService != null
				&& (!hasImportKwh && !hasImportKvarh && !hasExportKwh && !hasExportKvarh)) {
			throw new UserException
					("If there's a DCE contract, surely there must be some data to collect?");
		}
		setDceService(dceService);
		if (supplierAccount == null) {
			throw new UserException
					("An MPAN must have a supplier account.");
		}
		Set<SiteSupplyGeneration> siteSupplyGenerations = getSupplyGeneration()
				.getSiteSupplyGenerations();
		if (siteSupplyGenerations != null
				&& !siteSupplyGenerations.isEmpty()
				&& siteSupplyGenerations.iterator().next().getSite()
						.getOrganization().findSupplier(
								supplierAccount.getProvider().getId()) == null) {
			throw new UserException
					("The supplier account must be attached to the same organization as the MPAN.");
		}
		setSupplierAccount(supplierAccount);
		setSupplierService(supplierService);
		setHasImportKwh(hasImportKwh);
		setHasImportKvarh(hasImportKvarh);
		setHasExportKwh(hasExportKwh);
		setHasExportKvarh(hasExportKvarh);
		setAgreedSupplyCapacity(agreedSupplyCapacity);
	}

	public String toString() {
		return getMpanTop() + " " + getMpanCore();
	}

	public Node toXml(Document doc) throws InternalException, HttpException {
		setTypeName("mpan");
		Element element = (Element) super.toXml(doc);
		element.setAttributeNode(MonadBoolean.toXml(doc, "has-import-kwh",
				hasImportKwh));
		element.setAttributeNode(MonadBoolean.toXml(doc, "has-import-kvarh",
				hasImportKvarh));
		element.setAttributeNode(MonadBoolean.toXml(doc, "has-export-kwh",
				hasExportKwh));
		element.setAttributeNode(MonadBoolean.toXml(doc, "has-export-kvarh",
				hasExportKvarh));
		element.setAttributeNode(MonadInteger.toXml(doc,
				"agreed-supply-capacity", agreedSupplyCapacity));
		element.setAttributeNode(getMpanRaw().toXml(doc));
		return element;
	}

	public MonadUri getUri() {
		// TODO Auto-generated method stub
		return null;
	}

	public Urlable getChild(UriPathElement uriId) throws InternalException,
			HttpException {
		// TODO Auto-generated method stub
		return null;
	}

	public void httpGet(Invocation inv) throws DesignerException,
			InternalException, HttpException, DeployerException {
		// TODO Auto-generated method stub

	}

	public void httpPost(Invocation inv) throws InternalException,
			HttpException {
		// TODO Auto-generated method stub

	}

	public void httpDelete(Invocation inv) throws InternalException,
			DesignerException, HttpException, DeployerException {
		// TODO Auto-generated method stub

	}
	
	void delete() throws HttpException, InternalException {
		//check no invoices
		if (((Long) Hiber.session().createQuery("from InvoiceMpan invoiceMpan where invoiceMpan.mpan = :mpan").setEntity("mpan", this).uniqueResult()) > 0) {
			throw new UserException("An MPAN can't be deleted if still has invoices attached.");
		}
	}

	public HhdcContract getDceService(boolean isImport, boolean isKwh) {
		HhdcContract dceService = null;
		if (isImport) {
			if (isKwh) {
				if (hasImportKwh) {
					dceService = getDceService();
				}
			} else {
				if (hasImportKvarh) {
					dceService = getDceService();
				}
			}
		} else {
			if (isKwh) {
				if (hasExportKwh) {
					dceService = getDceService();
				}
			} else {
				if (hasExportKvarh) {
					dceService = getDceService();
				}
			}
		}
		return dceService;
	}

	public MpanRaw getMpanRaw() throws InternalException, HttpException {
		return new MpanRaw(getMpanTop().getProfileClass().getCode(), getMpanTop().getMeterTimeswitch()
				.getCode(), getMpanTop().getLlf().getCode(), getMpanCore().getCore());
	}
}