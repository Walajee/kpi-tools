
package pl.docusafe.tools.kpi.calendar;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Klasa bazowa dla implementacji KPI Calendar
 * @author wkutyla
 *
 */
public abstract class AbstractKpiCalendar implements KpiCalendar
{

	/**
	 * Data kalendarza
	 */
	protected Date time;
	/**
	 * Time wyrazony w obiekcie caledar
	 */
	protected Calendar cal;

	/**
	 * Konwertuje do obiektu kalendarz
	 * @param date
	 * @return
	 */
	protected Calendar convertToCalendar(Date date)
	{
		if (date==null)
			return null;
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(time);
		return calendar;
	}

	public void setTime(Date date)
	{
		time = date;
		cal = convertToCalendar(date);
	}

	public Date getTime()
	{
		return time;
	}

	public abstract boolean isWorkTime();

	public abstract Date nextEventDate(Date notAfterThan);

	public Date nextEventDate()
	{
		return nextEventDate(null);
	}



}
