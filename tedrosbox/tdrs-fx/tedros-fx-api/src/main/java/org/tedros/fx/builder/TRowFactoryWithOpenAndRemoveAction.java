/**
 * 
 */
package org.tedros.fx.builder;

import java.util.Arrays;
import java.util.List;

import org.tedros.fx.model.TModelView;

import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

/**
 * @author Davis Gordon
 *
 */
public class TRowFactoryWithOpenAndRemoveAction<M extends TModelView<?>> extends TContextMenuRowFactoryCallBackBuilder<M> {

	@Override
	List<MenuItem> getMenuItems(TableView<M> table, TableRow<M> row) {

		return Arrays.asList(TRowFactoryCalbackHelper.buildOpenMenuItems(table, row), 
				TRowFactoryCalbackHelper.buildRemoveMenuItems(table, row));
	}
	

}
