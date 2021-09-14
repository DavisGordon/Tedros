/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 25/02/2014
 */
package com.covidsemfome.module.acao.model;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class PessoaProfissaoCallback implements Callback<TableColumn, TableCell> {

	

	@Override
	public TableCell call(TableColumn tipo) {
		return new PessoaTableCell(2);
	}
	
}
