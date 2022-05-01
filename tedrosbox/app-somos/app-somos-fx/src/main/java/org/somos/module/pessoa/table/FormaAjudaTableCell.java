/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 25/02/2014
 */
package org.somos.module.pessoa.table;

import org.somos.model.FormaAjuda;

import com.tedros.fxapi.control.TTableCell;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class FormaAjudaTableCell extends TTableCell{

	public String processItem(Object item) {
		if(item!=null && item instanceof FormaAjuda) {
			FormaAjuda p = (FormaAjuda) item;
			return p.getTipo();
		}
		
		return ""; 
	}


}
