/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 25/02/2014
 */
package org.tedros.fx.control.tablecell;

import java.text.DateFormat;

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
public class TMediumDateTimeCallback implements Callback<TableColumn, TableCell> {

	@Override
	public TableCell call(TableColumn tipo) {
		return new TDateTimeTableCell(DateFormat.MEDIUM, DateFormat.MEDIUM);
	}
	
}
