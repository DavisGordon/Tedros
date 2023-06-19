/**
 * 
 */
package org.tedros.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 
 * The project date patterns
 * 
 * @author Davis Gordon
 *
 */
public class TDateUtil {

	public static final String DDMMYYYY = "dd/MM/yyyy";
	public static final String DDMMYYYY_HHMM = "dd/MM/yyyy HH:mm";
	
	private int dateStyle = -1;
	private int timeStyle = -1;
	private Locale locale;
	
	private TDateUtil(Locale locale) {
		this.locale = locale;
	}
	
	public static TDateUtil create(Locale locale) {
		return new TDateUtil(locale);
	}
	
	/**
	 * @param dateStyle the dateStyle to set
	 */
	public TDateUtil setDateStyle(int dateStyle) {
		this.dateStyle = dateStyle;
		return this;
	}

	/**
	 * @param timeStyle the timeStyle to set
	 */
	public TDateUtil setTimeStyle(int timeStyle) {
		this.timeStyle = timeStyle;
		return this;
	}
	
	public String format(Date date) {
		if(this.dateStyle>=0 && this.timeStyle<0)
			return formatDate(date, dateStyle, locale);
		else if(this.dateStyle<0 && this.timeStyle>=0)
			return formatTime(date, timeStyle, locale);
		else if(this.dateStyle>=0 && this.timeStyle>=0)
			return formatDateTime(date, dateStyle, timeStyle, locale);
		else
			return formatDate(date, locale);
	}

	public static synchronized String formatShortDateTime(Date data, Locale locale){
		return formatDateTime(data, DateFormat.SHORT, DateFormat.SHORT, locale);
	}
	
	public static synchronized String formatMediumDateTime(Date data, Locale locale){
		return formatDateTime(data, DateFormat.MEDIUM, DateFormat.MEDIUM, locale);
	}
	
	public static synchronized String formatLongDateTime(Date data, Locale locale){
		return formatDateTime(data, DateFormat.LONG, DateFormat.LONG, locale);
	}

	public static synchronized String formatFullgDateTime(Date data, Locale locale){
		return formatDateTime(data, DateFormat.FULL, DateFormat.FULL, locale);
	}
	

	public static synchronized String formatDateTime(Date data, Locale locale){
		return formatDateTime(data, DateFormat.DEFAULT, DateFormat.DEFAULT, locale);
	}
	
	public static synchronized String formatDateTime(Date data, int dateStyle, int timeStyle, Locale locale){
		DateFormat df = DateFormat.getDateTimeInstance(dateStyle, timeStyle, locale);
		df.setLenient(false);
		return df.format(data);
	}
	
	public static synchronized String formatShortDate(Date data, Locale locale){
		return formatDate(data, DateFormat.SHORT, locale);
	}
	
	public static synchronized String formatMediumDate(Date data, Locale locale){
		return formatDate(data, DateFormat.MEDIUM, locale);
	}
	
	public static synchronized String formatLongDate(Date data, Locale locale){
		return formatDate(data, DateFormat.LONG, locale);
	}

	public static synchronized String formatFullgDate(Date data, Locale locale){
		return formatDate(data, DateFormat.FULL, locale);
	}
	

	public static synchronized String formatDate(Date data, Locale locale){
		return formatDate(data, DateFormat.DEFAULT, locale);
	}
	
	public static synchronized String formatDate(Date data, int dateStyle, Locale locale){
		DateFormat df = DateFormat.getDateInstance(dateStyle, locale);
		df.setLenient(false);
		return df.format(data);
	}
	
	public static synchronized String formatShortTime(Date data, Locale locale){
		return formatTime(data, DateFormat.SHORT, locale);
	}
	
	public static synchronized String formatMediumTime(Date data, Locale locale){
		return formatTime(data, DateFormat.MEDIUM, locale);
	}
	
	public static synchronized String formatLongTime(Date data, Locale locale){
		return formatTime(data, DateFormat.LONG, locale);
	}

	public static synchronized String formatFullgTime(Date data, Locale locale){
		return formatTime(data, DateFormat.FULL, locale);
	}
	

	public static synchronized String formatTime(Date data, Locale locale){
		return formatTime(data, DateFormat.DEFAULT, locale);
	}

	public static synchronized String formatTime(Date data, int timetyle, Locale locale){
		DateFormat df = DateFormat.getTimeInstance(timetyle, locale);
		df.setLenient(false);
		return df.format(data);
	}
	
	public static synchronized String format(Date data, String pattern){
		DateFormat df = new SimpleDateFormat(pattern);
		df.setLenient(false);
		return df.format(data);
	}

	public static synchronized Date getDate(String data, String pattern) throws ParseException{
		DateFormat df = new SimpleDateFormat(pattern);
		df.setLenient(false);
		return df.parse(data);
	}
}
