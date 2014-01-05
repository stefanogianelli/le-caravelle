package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataUtils {
	
	/**
	 * Si occupa di formattare la data in dd/MM/yyyy (es.: 28/12/2013)
	 * @param data La data da formattare
	 * @return La stringa del formato desiderato
	 */
	public static String getData (Date data) {
		return new SimpleDateFormat("dd/MM/yyy").format(data);
	}
	
	/**
	 * Restituisce la data odierna
	 * @return La data odierna
	 */
	public static Date getDataOdierna () {
		Calendar dataOdierna = Calendar.getInstance();
		dataOdierna.set(Calendar.HOUR_OF_DAY, 0);
		return dataOdierna.getTime();
	}
	
	/**
	 * Fa il parsing di una stringa e ne restituisce la data
	 * @param data La data da convertitre
	 * @return L'oggetto java.util.Date corrispendente
	 */
	public static Date parseData (String data) {
		try {
			return new SimpleDateFormat("dd/MM/yyyy").parse(data);
		} catch (ParseException e) {
			System.out.println("Errore nel parsing della data " + data);
		}
		return null;
	}
	
	/**
	 * Restituisce la data risultante dalla somma della data iniziale e la durata
	 * @param data La data iniziale
	 * @param durata I giorni da sommare
	 * @return La data risultante
	 */
	public static Date sommaGiorni (Date data, int durata) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		cal.add(Calendar.DATE, durata);
		return cal.getTime();
	}
}
