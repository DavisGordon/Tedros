/**
 * 
 */
package org.somos.web.rest.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.tedros.util.TDateUtil;

/**
 * @author Davis Gordon
 *
 */
public final class ApiUtils {
	

	public static String formatDateHourToView(Date data){
		if(data==null)
			return null;
		
		String pattern = "dd/MM/yyyy 'às' HH:mm";
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(data);
	}
	
	/**
	 * @param data
	 * @return
	 */
	public static String formatToDate(Date data) {
		return data != null 
				? TDateUtil.getFormatedDate(data, "yyyy-MM-dd") 
						: null;
	}
	
	
	/**
	 * @param data
	 * @return
	 */
	public static String formatToDateTime(Date data) {
		return data != null 
				? TDateUtil.getFormatedDate(data, "yyyy-MM-dd'T'HH:mm") 
						: null;
	}
	
	public static Date convertToDate(String dt) {
		if(dt!=null) {
			try {
				return TDateUtil.getDate(dt, "yyyy-MM-dd");
			}catch(ParseException e) {
				e.printStackTrace();
				System.err.println("Não foi possivel converter a data "+dt);
			}
		}
		return null;
	}
	
	public static Date convertToDateTime(String dt) {
		if(dt!=null) {
			try {
				return TDateUtil.getDate(dt, "yyyy-MM-dd'T'HH:mm");
			}catch(ParseException e) {
				e.printStackTrace();
				System.err.println("Não foi possivel converter a data "+dt);
			}
		}
		return null;
	}
	
}
