package pl.docusafe.tools.kpi.calendar;

import java.util.Date;

/**
 * Interfejs reprezentujacy kalendarz roboczy
 * Jest to obiekt, ktory posiada okreslony czas wolny i czas roboczy
 * Kalendarz ten pozwoli obliczyc parametry czasowe (np. czas wykonania czynnosci)
 * uwzgledniajac tylko i wylacznie czas roboczy
 * @author wkutyla
 *
 */
public interface KpiCalendar
{
	/**
	 * Ustawia biezaca date obiektu klasy Date
	 * @param date
	 */
	public void setTime(Date date);

	/**
	 * Pobiera bierzaca cza kalendarza
	 * @return
	 */
	public Date getTime();
	/**
	 * Czy czas przechowywany w
	 * wartosci time jest czasem
	 * roboczym czy nie
	 * Jezeli time jest czasem granicznym to interesuje nas sytuacja w czasie wiekszym niz
	 * time
	 * Przyklad. Dzien roboczy konczy sie o 17:00
	 * Dla godziny 16:59 - time jest roboczy
	 * Dla 17:00 - wolny
	 * @return
	 */
	public boolean isWorkTime();

	/**
	 * Data nastepnego zdarzenia kalendarza,
	 * Moze, ale nie musi to oznaczac czas zmiany z
	 * czasu roboczego, na wolny
	 * NextEvent Date nie moze jedank pomijac zadnego okresu wolnego
	 * @return
	 */
	public Date nextEventDate();

	/**
	 * Data nastepnego zdarzenia
	 * Nie pozniejszego jednak niz notAfterThan
	 * @param notAfterThan
	 * @return
	 */
	public Date nextEventDate(Date notAfterThan);
}
