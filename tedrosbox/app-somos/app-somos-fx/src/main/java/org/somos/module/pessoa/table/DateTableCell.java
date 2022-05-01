/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 25/02/2014
 */
package org.somos.module.pessoa.table;

import java.util.Date;

import com.tedros.fxapi.control.TTableCell;
import com.tedros.util.TDateUtil;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class DateTableCell extends TTableCell{

	public String processItem(Object item) {
		if(item!=null && item instanceof Date) {
			Date p = (Date) item;
			return TDateUtil.getFormatedDate(p, TDateUtil.DDMMYYYY);
		}
		
		return ""; 
	}


}
