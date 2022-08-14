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
public class TDateTableCell extends TTableCell{

	private int dateStyle;
	
	/**
	 * @param dateStyle
	 */
	public TDateTableCell(int dateStyle) {
		this.dateStyle = dateStyle;
	}
	
	public String processItem(Object item) {
		if(item!=null && item instanceof Date) {
			Date dt = (Date) item;
			return DateFormat
					.getDateInstance(dateStyle, TLanguage.getLocale())
					.format(dt);
		}
		
		return ""; 
	}


}
