/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 25/02/2014
 */
package org.somos.module.pessoa.table;

import org.somos.model.Pessoa;

import com.tedros.fxapi.control.TTableCell;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class PessoaTableCell extends TTableCell{

	private String text;
	
	public PessoaTableCell(String text) {
		this.text = text;
	}

	public String processItem(Object item) {
		if(item!=null && item instanceof Pessoa) {
			Pessoa p = (Pessoa) item;
			switch (text) {
			case "Nome" : return p.getNome();
			case "Profissão" : return p.getProfissao()!=null ? p.getProfissao() : "";
			case "Contato" : return p.getTodosContatos();
			}
			
		}
		
		return ""; 
	}


}
