/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.docusafe.tools.kpi.calendar;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Prosty kalendarz KPI dzialajacy w oparciu o nastepujace zalozenia
 * niedziele i soboty wolne
 * Dzien roboczy w pelnych godzinach od startHour to stopHour
 * @author wkutyla
 */
public class SimpleKpiCalendar extends AbstractKpiCalendar
{

	/**
	 * Godzina rozpoczecia pracy
	 */
	private Integer startHour;
	/**
	 * Godzina za konczenia pracy
	 */
	private Integer stopHour;

	/**
	 * Konstruktor ustawiajacy typoww dzien pracy 9-17
	 */
	public SimpleKpiCalendar()
	{
		super();
		startHour = 9;
		stopHour = 17;
	}

	/**
	 * Konstrukor wskazujacy
	 * @param from
	 * @param to
	 */
	public SimpleKpiCalendar(Integer from, Integer to)
	{
		super();
		if (from == null || to == null)
			throw new IllegalArgumentException("Parametry nie moga byc null");
		if (from.intValue()>=to.intValue())
			throw new java.lang.IllegalArgumentException("from nie wieksze niz to");
		startHour = from;
		stopHour = to;

	}

	public Integer getStartHour()
	{
		return startHour;
	}

	public void setStartHour(Integer startHour) {
		this.startHour = startHour;
	}

	public Integer getStopHour() {
		return stopHour;
	}

	public void setStopHour(Integer stopHour) {
		this.stopHour = stopHour;
	}

	/**
	 * Czy czas od momentu time w gore jest roboczy
	 * @return
	 */
	public boolean isWorkTime()
	{
		return isWorkTime(cal);
	}

	/**
	 * Czy calendat wskazuje na czas roboczy czy wolny
	 * W przypadku czasu bedacego na graniczy przedzialow
	 * Interesuje nas czas bedacy po prawej stronie (nowszy)
	 * @param calendar
	 * @return
	 */
	public boolean isWorkTime(Calendar calendar)
	{
		if (calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)
			return false;
		if (calendar.get(Calendar.HOUR_OF_DAY)<startHour.intValue() || calendar.get(Calendar.HOUR_OF_DAY)>=stopHour.intValue())
			return false;

		return true;
	}

	@Override
	public Date nextEventDate(java.util.Date notAfterTo)
	{
		boolean work = isWorkTime();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(time);
		boolean doIt = true;
		while (doIt)
		{
			if (calendar.get(Calendar.HOUR_OF_DAY)<startHour.intValue())
			{
				calendar.set(Calendar.HOUR_OF_DAY, startHour.intValue());
				calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND,0);
                calendar.set(Calendar.MILLISECOND, 0);
			}
			else if (calendar.get(Calendar.HOUR_OF_DAY)<stopHour.intValue())
			{
				calendar.set(Calendar.HOUR_OF_DAY, stopHour.intValue());
				calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND,0);
                calendar.set(Calendar.MILLISECOND, 0);
			}
			else
			{
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND,0);
                calendar.set(Calendar.MILLISECOND, 0);

				calendar.roll(Calendar.DAY_OF_YEAR, 1);
				if (calendar.get(Calendar.DAY_OF_YEAR)==1)
                    calendar.roll(Calendar.YEAR,1);
			}
			//rob jest obecny czas ma status taki jak w przypadku poczatkowego
			doIt = work == isWorkTime(calendar);
		}
		Date resp = calendar.getTime();
		if (notAfterTo!=null && notAfterTo.before(resp))
			resp = notAfterTo;
		return resp;
	}


}

