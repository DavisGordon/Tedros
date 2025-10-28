/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 25/02/2014
 */
package org.tedros.fx.control.tablecell;

import java.text.DateFormat;
import java.util.Date;

import org.tedros.core.TLanguage;
import org.tedros.fx.control.TTableCell;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class TDateTimeTableCell extends TTableCell{
	
	private int dateStyle;
	private int timeStyle;
	
	/**
	 * @param dateStyle
	 * @param timeStyle
	 */
	public TDateTimeTableCell(int dateStyle, int timeStyle) {
		this.dateStyle = dateStyle;
		this.timeStyle = timeStyle;
	}

	public String processItem(Object item) {
		if(item!=null && item instanceof Date) {
			Date dt = (Date) item;
			return DateFormat
					.getDateTimeInstance(dateStyle, timeStyle, TLanguage.getLocale())
					.format(dt);
		}
		
		return ""; 
	}


}
