/**
 * 
 */
package org.tedros.fx.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.tedros.core.TLanguage;
import org.tedros.core.context.TLoader;
import org.tedros.core.context.TedrosModuleLoader;
import org.tedros.fx.TFxKey;
import org.tedros.fx.model.TModelView;
import org.tedros.server.model.ITModel;

import javafx.beans.binding.Bindings;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

/**
 * @author Davis Gordon
 *
 */
public class TOpenModelRowFactoryCallBackBuilder<M extends TModelView<?>> extends TContextMenuRowFactoryCallBackBuilder<M> {

	private TLanguage iE = TLanguage.getInstance();
	
	@Override
	List<MenuItem> getMenuItems(TableView<M> table, TableRow<M> row) {

		final MenuItem open = new MenuItem(iE.getString(TFxKey.BUTTON_OPEN));
		open.setOnAction(e->{
			M mv = row.getItem();
			List<TLoader> l = TedrosModuleLoader.getInstance()
			.getLoader(mv.getModel());
			TRowFactoryCalbackHelper.callLoader(l);
		});
		
		open.disableProperty().bind(
				  Bindings.size(table.getSelectionModel().getSelectedItems()).greaterThan(1));
		
		final MenuItem edit = new MenuItem(iE.getString(TFxKey.BUTTON_EDIT));
		edit.setOnAction(e->{
			List<ITModel> models = new ArrayList<>();
			table.getSelectionModel().getSelectedItems().forEach(mv->{
				models.add(mv.getModel());
			});
			List<TLoader> l = TedrosModuleLoader.getInstance()
			.getLoader(models);
			TRowFactoryCalbackHelper.callLoader(l);
		});
		
		edit.disableProperty().bind(
				  Bindings.size(table.getSelectionModel().getSelectedItems()).lessThan(2));
		
		return Arrays.asList(open, edit);
	}
	
	

}
