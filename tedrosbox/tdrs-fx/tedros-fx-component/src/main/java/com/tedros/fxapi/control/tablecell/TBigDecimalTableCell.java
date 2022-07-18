/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 25/02/2014
 */
package com.tedros.fxapi.control.tablecell;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import com.tedros.core.TLanguage;
import com.tedros.core.context.TedrosContext;
import com.tedros.fxapi.control.TTableCell;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class TBigDecimalTableCell extends TTableCell{

	private boolean currency;
	
	/**
	 * @param dateStyle
	 */
	public TBigDecimalTableCell(boolean currency) {
		this.currency = currency;
	}
	
	public String processItem(Object item) {
		if(item!=null && item instanceof BigDecimal) {
			BigDecimal v = (BigDecimal) item;
			Locale l = new Locale(TLanguage.getLocale().toLanguageTag(), TedrosContext.getCountryIso2());
		    if(currency) {return NumberFormat
		        		.getCurrencyInstance(l)
		        		.format(v);
			}else
				return NumberFormat
						.getInstance(l)
						.format(v);
		}
		
		return ""; 
	}


}
