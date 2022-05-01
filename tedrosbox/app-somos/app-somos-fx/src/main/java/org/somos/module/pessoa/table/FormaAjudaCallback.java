/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 25/02/2014
 */
package org.somos.module.pessoa.table;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class FormaAjudaCallback implements Callback<TableColumn, TableCell> {

	

	@Override
	public TableCell call(TableColumn tipo) {
		return new FormaAjudaTableCell();
	}
	
}
