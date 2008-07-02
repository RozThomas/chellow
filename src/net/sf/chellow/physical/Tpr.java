package net.sf.chellow.physical;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

import net.sf.chellow.monad.Hiber;
import net.sf.chellow.monad.HttpException;
import net.sf.chellow.monad.InternalException;
import net.sf.chellow.monad.Invocation;
import net.sf.chellow.monad.MonadUtils;
import net.sf.chellow.monad.NotFoundException;
import net.sf.chellow.monad.Urlable;
import net.sf.chellow.monad.UserException;
import net.sf.chellow.monad.XmlTree;
import net.sf.chellow.monad.types.MonadUri;
import net.sf.chellow.monad.types.UriPathElement;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.Ostermiller.util.CSVParser;

public class Tpr extends PersistentEntity {
	static public Tpr findTpr(String code) {
		return (Tpr) Hiber.session().createQuery(
				"from Tpr tpr where tpr.code = :code").setString("code", code)
				.uniqueResult();
	}

	static public Tpr getTpr(String code) throws HttpException {
		Tpr tpr = findTpr(code);
		if (tpr == null) {
			throw new UserException("Can't find a TPR with code '" + code
					+ "'.");
		}
		return tpr;
	}

	static public Tpr getTpr(long id) throws NotFoundException,
			InternalException {
		Tpr tpr = (Tpr) Hiber.session().get(Tpr.class, id);
		if (tpr == null) {
			throw new NotFoundException();
		}
		return tpr;
	}

	static public void loadFromCsv() throws HttpException {
		try {
			ClassLoader classLoader = Participant.class.getClassLoader();
			CSVParser parser = new CSVParser(new InputStreamReader(classLoader
					.getResource(
							"net/sf/chellow/physical/TimePatternRegime.csv")
					.openStream(), "UTF-8"));
			parser.setCommentStart("#;!");
			parser.setEscapes("nrtf", "\n\r\t\f");
			String[] titles = parser.getLine();
			if (titles.length < 3
					|| !titles[0].trim().equals("Time Pattern Regime Id")
					|| !titles[1].trim().equals("Tele-switch/Clock Indicator")
					|| !titles[2].trim().equals("GMT Indicator")) {
				throw new UserException(
						"The first line of the CSV must contain the titles "
								+ "Time Pattern Regime Id, Tele-switch/Clock Indicator, GMT Indicator");
			}
			for (String[] values = parser.getLine(); values != null; values = parser
					.getLine()) {
				Hiber.session().save(
						new Tpr(values[0], values[1].equals("S"), values[2]
								.equals("Y")));
			}
		} catch (UnsupportedEncodingException e) {
			throw new InternalException(e);
		} catch (IOException e) {
			throw new InternalException(e);
		}
	}

	private String code;
	private boolean isTeleswitch;
	private boolean isGmt;

	private Set<ClockInterval> clockIntervals;

	private Set<MeasurementRequirement> measurementRequirements;

	public Tpr() {
	}

	public Tpr(String code, boolean isTeleswitch, boolean isGmt) {
		setClockIntervals(new HashSet<ClockInterval>());
		setCode(code);
		setIsTeleswitch(isTeleswitch);
		setIsGmt(isGmt);
	}

	public Set<ClockInterval> getClockIntervals() {
		return clockIntervals;
	}

	void setClockIntervals(Set<ClockInterval> clockIntervals) {
		this.clockIntervals = clockIntervals;
	}

	public Set<MeasurementRequirement> getMeasurementRequirements() {
		return measurementRequirements;
	}

	void setMeasurementRequirements(
			Set<MeasurementRequirement> measurementRequirements) {
		this.measurementRequirements = measurementRequirements;
	}

	String getCode() {
		return code;
	}

	void setCode(String code) {
		this.code = code;
	}

	public boolean getIsTeleswitch() {
		return isTeleswitch;
	}

	void setIsTeleswitch(boolean isTeleswitch) {
		this.isTeleswitch = isTeleswitch;
	}

	public boolean getIsGmt() {
		return isGmt;
	}

	void setIsGmt(boolean isGmt) {
		this.isGmt = isGmt;
	}

	public Urlable getChild(UriPathElement uriId) throws HttpException {
		if (ClockIntervals.URI_ID.equals(uriId)) {
			return new ClockIntervals(this);
		} else {
			throw new NotFoundException();
		}
	}

	public MonadUri getUri() throws InternalException {
		// TODO Auto-generated method stub
		return null;
	}

	public void httpDelete(Invocation inv) throws HttpException {
		// TODO Auto-generated method stub
	}

	@SuppressWarnings("unchecked")
	public void httpGet(Invocation inv) throws HttpException {
		Document doc = MonadUtils.newSourceDocument();
		Element source = doc.getDocumentElement();
		source.appendChild(toXml(doc, new XmlTree("measurementRequirements", new XmlTree("ssc")).put("clockIntervals")));
		inv.sendOk(doc);
	}

	public void httpPost(Invocation inv) throws HttpException {
		// TODO Auto-generated method stub
	}

	public ClockInterval insertClockInterval(int dayOfWeek, int startDay,
			int startMonth, int endDay, int endMonth, int startHour,
			int startMinute, int endHour, int endMinute) {
		ClockInterval interval = new ClockInterval(this, dayOfWeek, startDay,
				startMonth, endDay, endMonth, startHour, startMinute, endHour,
				endMinute);
		Hiber.session().save(interval);
		Hiber.session().flush();
		return interval;
	}

	public Node toXml(Document doc) throws HttpException {
		setTypeName("tpr");
		Element element = (Element) super.toXml(doc);

		element.setAttribute("code", code);
		element.setAttribute("is-teleswitch", String.valueOf(isTeleswitch));
		element.setAttribute("is-gmt", String.valueOf(isGmt));
		return element;
	}

	public String toString() {
		return "Code: " + code + " Is Teleswitch?: " + isTeleswitch
				+ " Is GMT?: " + isGmt;
	}
}
