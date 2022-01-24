/**
 * 
 */
package org.somos.module.estoque.model;

import com.tedros.fxapi.control.tablecell.TNumberSpinnerFieldTableCell;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * @author Davis Gordon
 *
 */
public class QtdAjusteCallBack implements Callback<TableColumn, TableCell> {

	@Override
	public TableCell call(TableColumn param) {
		return new TNumberSpinnerFieldTableCell<EstoqueItemModelView>();
	}

}
