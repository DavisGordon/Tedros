/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 25/02/2014
 */
package com.tedros.global.brasil.module.pessoa.table;

import javafx.beans.property.SimpleStringProperty;

import com.tedros.fxapi.control.TTableCell;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TipoPessoaTableCell<S, T> extends TTableCell<S, T> {

	@Override
	public String processItem(Object item) {
		
		SimpleStringProperty tipoPessoa = (SimpleStringProperty) item;
		if(tipoPessoa.get()!=null && tipoPessoa.get().equals("F"))
			return "Fisica";
		if(tipoPessoa.get()!=null && tipoPessoa.get().equals("J"))
			return "Juridica";
		
		return ""; 
	}
	
}
