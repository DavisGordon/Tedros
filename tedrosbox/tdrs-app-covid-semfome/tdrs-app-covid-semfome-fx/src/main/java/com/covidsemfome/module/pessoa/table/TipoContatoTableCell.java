/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 25/02/2014
 */
package com.covidsemfome.module.pessoa.table;

import javafx.beans.property.SimpleStringProperty;

import com.tedros.core.TInternationalizationEngine;
import com.tedros.fxapi.control.TTableCell;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TipoContatoTableCell<S, T> extends TTableCell<S, T> {

	@Override
	public String processItem(Object item) {
		TInternationalizationEngine iEn = TInternationalizationEngine.getInstance(null);
		SimpleStringProperty tipo = (SimpleStringProperty) item;
		
		if(tipo.get()!=null )
			switch(tipo.get()) {
			case "1":
				return iEn.getString("#{label.email}");
			case "2":
				return iEn.getString("#{label.celnumber}");	
			case "3":
				return iEn.getString("#{label.housenumber}");
			case "4":
				return iEn.getString("#{label.worknumber}");
			}
		
		
		return ""; 
	}
	
}
