/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 25/02/2014
 */
package com.covidsemfome.module.acao.model;

import com.covidsemfome.model.Contato;
import com.covidsemfome.model.Pessoa;
import com.tedros.core.TInternationalizationEngine;
import com.tedros.fxapi.control.TTableCell;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class PessoaTableCell extends TTableCell{

	private int field;
	
	/**
	 * @param field
	 */
	public PessoaTableCell(int field) {
		this.field = field;
	}

	public String processItem(Object item) {
		if(item!=null && item instanceof Pessoa) {
			Pessoa p = (Pessoa) item;
			switch (field) {
			case 1 : return p.getNome();
			case 2 : return p.getProfissao()!=null ? p.getProfissao() : "";
			case 3 : 
				String t = "";
				if(p.getContatos()!=null && !p.getContatos().isEmpty()) {
					for(Contato c : p.getContatos())
						t += t.equals("") ? c.getDescricao() : ", "+c.getDescricao();
				}
				return t;
			}
			
		}
		
		return ""; 
	}


}
