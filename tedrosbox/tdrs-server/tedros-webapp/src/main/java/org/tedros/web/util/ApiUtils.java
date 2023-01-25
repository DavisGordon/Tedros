/**
 * 
 */
package org.tedros.web.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Davis Gordon
 *
 */
public final class ApiUtils {

	public static String formatDateTimeToView(Locale locale, Date date) {
		if (date == null)
			return null;
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, locale);
		return df.format(date);
	}

	public static String formatDateToView(Locale locale, Date date) {
		if (date == null)
			return null;

		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
		return df.format(date);
	}

	/**
	 * @param data
	 * @return
	 */
	public static String formatToDate(Date date) {
		return date != null ? format("yyyy-MM-dd", date) : null;
	}

	/**
	 * @param data
	 * @return
	 */
	public static String formatToDateTime(Date date) {
		return date != null ? format("yyyy-MM-dd'T'HH:mm", date) : null;
	}

	public static Date convertToDate(String dt) throws ParseException {
		return parse("yyyy-MM-dd", dt);
	}

	public static Date convertToDateTime(String dt) throws ParseException {
		return parse("yyyy-MM-dd'T'HH:mm", dt);
	}

	private static Date parse(String pattern, String source) throws ParseException {
		return new SimpleDateFormat(pattern).parse(source);
	}

	private static String format(String pattern, Date date) {
		return new SimpleDateFormat(pattern).format(date);
	}

}
