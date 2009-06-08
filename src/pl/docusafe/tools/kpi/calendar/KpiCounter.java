package pl.docusafe.tools.kpi.calendar;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Klasa liczy czas jaki uplynal od punktu A do punktu B
 * ale tylko z uwzglednieniem czasu roboczego
 * @author wkutyla
 *
 */
public class KpiCounter
{
	static final Long MINUTE_IN_MILLIS = 60l * 1000l;
	static final Long HOUR_IN_MILLIS = 60l * MINUTE_IN_MILLIS;
	static final Long DAY_IN_MILLIS = 24l * HOUR_IN_MILLIS;

	/**
	 * Data od ktorej liczymy
	 */
	private Date dateFrom;
	/**
	 * Data do ktorej liczymy
	 */
	private Date dateTo = new Date();

	/**
	 * Kalendarz w oparciu o ktory wykonujemy obliczenia
	 */
	private KpiCalendar kpiCalendar = null;
	//private KpiCalendar kpiCalendar = new KpiCalendar():

	private BigDecimal duration = null;

	public KpiCounter()
	{
		super();
	}
	public KpiCounter(KpiCalendar calendar, Date from,Date to)
	{
		this();
		kpiCalendar = calendar;
		dateFrom = from;
		dateTo = to;
		if (dateTo==null)
			dateTo = new Date();
	}

	/**
	 * Liczy czas od A do B w MS
	 * @return
	 */
	public long countDuration()
	{
		if (dateFrom == null || dateTo == null)
			throw new IllegalStateException("Jedna z dat == null");
		if (dateFrom.after(dateTo))
			throw new IllegalStateException("Data from nowsza niz to");
		if (kpiCalendar==null)
			throw new java.lang.IllegalArgumentException("Nie okreslono kalendarza KPI");
		long resp = 0;


		Date currentTime = dateFrom;
		while (currentTime.before(dateTo))
		{
			kpiCalendar.setTime(currentTime);
			boolean work = kpiCalendar.isWorkTime();
			Date nextTime = kpiCalendar.nextEventDate(dateTo);
			if (work)
			{
				resp = resp + nextTime.getTime()-currentTime.getTime();
			}
			currentTime = nextTime;
		}
		duration = new BigDecimal(resp);
		return resp;
	}

	/**
	 * Czas wyrazony w minutach
	 * @return
	 */
	public int durationInMinutes()
	{
		if (duration==null)
			throw new IllegalStateException("Nie wykonano obliczen");


		BigDecimal periodInMillis = new BigDecimal(MINUTE_IN_MILLIS);

		BigDecimal resp = duration.divide(periodInMillis,0,BigDecimal.ROUND_DOWN);
		return resp.intValue();
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public KpiCalendar getKpiCalendar() {
		return kpiCalendar;
	}

	public void setKpiCalendar(KpiCalendar kpiCalendar) {
		this.kpiCalendar = kpiCalendar;
	}

}
