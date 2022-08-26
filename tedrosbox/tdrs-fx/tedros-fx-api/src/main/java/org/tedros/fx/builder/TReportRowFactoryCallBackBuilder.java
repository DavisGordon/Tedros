/**
 * 
 */
package org.tedros.fx.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.tedros.core.TLanguage;
import org.tedros.core.annotation.TLoadable;
import org.tedros.core.context.TLoader;
import org.tedros.core.context.TModuleContext;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.context.TedrosModuleLoader;
import org.tedros.core.message.TMessage;
import org.tedros.core.message.TMessageType;
import org.tedros.fx.TFxKey;
import org.tedros.fx.modal.TMessageBox;
import org.tedros.fx.presenter.model.TModelView;
import org.tedros.server.model.ITModel;
import org.tedros.server.model.ITReportItemModel;

import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

/**
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class TReportRowFactoryCallBackBuilder<M extends TModelView<? extends ITReportItemModel>> 
extends TContextMenuRowFactoryCallBackBuilder<M> {

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private TLanguage iE = TLanguage.getInstance();
	
	@Override
	List<MenuItem> getMenuItems(TableView<M> table, TableRow<M> row) {
		
		
		final MenuItem open = new MenuItem(iE.getString(TFxKey.BUTTON_OPEN));
		open.setOnAction(e->{
			M mv = row.getItem();
			List<TLoader> l = TedrosModuleLoader.getInstance()
			.getLoader(mv.getModel().getModel());
			callLoader(l);
		});
		
		open.disableProperty().bind(
				  Bindings.size(table.getSelectionModel().getSelectedItems()).greaterThan(1));
		
		final MenuItem edit = new MenuItem(iE.getString(TFxKey.BUTTON_EDIT));
		edit.setOnAction(e->{
			List<ITModel> models = new ArrayList<>();
			table.getSelectionModel().getSelectedItems().forEach(mv->{
				models.add(mv.getModel().getModel());
			});
			List<TLoader> l = TedrosModuleLoader.getInstance()
			.getLoader(models);
			callLoader(l);
		});
		
		edit.disableProperty().bind(
				  Bindings.size(table.getSelectionModel().getSelectedItems()).lessThan(2));
		
		final MenuItem remove  = new MenuItem(iE.getString(TFxKey.BUTTON_REMOVE));
		remove.setOnAction(ev-> {
		table.getItems().removeAll(
				table.getSelectionModel().getSelectedItems());
		});
		// disable this menu item if nothing is selected:
		remove.disableProperty().bind(
		  Bindings.isEmpty(table.getSelectionModel().getSelectedItems()));
		
		return Arrays.asList(open, edit, remove);
	}

	/**
	 * @param iE
	 * @param mv
	 * @param l
	 */
	private void callLoader(List<TLoader> l) {
		if(l.isEmpty()) {
			LOGGER.severe("The model must be setting as loadable, see "+TLoadable.class.getSimpleName());
			TedrosContext.showMessage(new TMessage(TMessageType.ERROR, iE.getString(TFxKey.MESSAGE_ERROR)));
		}else {
			if(l.size()==1) {
				TLoader r = l.get(0);
				if(!r.isLoadable()) {
					LOGGER.severe(r.getMessage());
					TedrosContext.showMessage(new TMessage(TMessageType.ERROR, iE.getString(TFxKey.MESSAGE_ERROR)));
				}else {
					r.loadInModule();
				}
			}else {
				TMessageBox box = null;
				for(TLoader r : l) {
					if(!r.isLoadable()) {
						LOGGER.severe(r.getMessage());
					}else {
						TModuleContext ctx = TedrosAppManager.getInstance()
						.getModuleContext(r.getModuleType());
						
						if(box==null)
							box = new TMessageBox(iE.getString(TFxKey.MESSAGE_CHOOSE_ONE), null);

						box.setAlignment(Pos.CENTER);
						String name = ctx.getModuleDescriptor().getModuleName();
						String btn = iE.getString(TFxKey.BUTTON_OPEN);
						box.tAddMessage(name, btn, ev->{
							TedrosContext.hideModal();
							try {
								r.loadInModule();
							}catch(Exception ex) {
								LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
								TedrosContext.showMessage(new TMessage(TMessageType.ERROR, iE.getString(TFxKey.MESSAGE_ERROR)));
							}
						});
					}
				}
				
				if(box==null)
					TedrosContext.showMessage(new TMessage(TMessageType.ERROR, iE.getString(TFxKey.MESSAGE_ERROR)));
				else
					TedrosContext.showModal(box);
			}
		}
	}

}
