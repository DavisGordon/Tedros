/**
 * 
 */
package com.tedros.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Davis Gordon
 *
 */
public class TDateUtil {

	public static final String DDMMYYYY = "dd/MM/yyyy";
	public static final String DDMMYYYY_HHMM = "dd/MM/yyyy HH:mm";
	
	/**
	 * 
	 */
	public TDateUtil() {
		// TODO Auto-generated constructor stub
	}
	
	public static synchronized String getFormatedDate(Date data, String pattern){
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(data);
	}

	public static synchronized Date getDate(String data, String pattern) throws ParseException{
		DateFormat df = new SimpleDateFormat(pattern);
		return df.parse(data);
	}
}
