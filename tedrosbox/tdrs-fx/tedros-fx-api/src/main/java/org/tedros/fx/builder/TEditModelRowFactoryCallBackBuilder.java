/**
 * 
 */
package org.tedros.fx.builder;

import java.util.Arrays;
import java.util.List;

import org.tedros.core.ModalMessage;
import org.tedros.core.TLanguage;
import org.tedros.core.annotation.TLoadable;
import org.tedros.core.context.TLoader;
import org.tedros.core.context.TModuleContext;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.context.TedrosModuleLoader;
import org.tedros.core.logging.TLog;
import org.tedros.fx.TFxKey;
import org.tedros.fx.control.TButton;
import org.tedros.fx.control.TLabel;
import org.tedros.fx.modal.TModalPane;
import org.tedros.fx.presenter.model.TModelView;

import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author Davis Gordon
 *
 */
public class TEditModelRowFactoryCallBackBuilder<M extends TModelView<?>> extends TContextMenuRowFactoryCallBackBuilder<M> {

	@Override
	List<MenuItem> getMenuItems(TableView<M> table, TableRow<M> row) {
		TLanguage iE = TLanguage.getInstance();
		MenuItem edit = new MenuItem(iE.getString(TFxKey.BUTTON_EDIT));
		edit.setOnAction(e->{
			M mv = row.getItem();
			
			List<TLoader> l = TedrosModuleLoader.getInstance()
			.getLoader(mv.getModel());
			if(l.isEmpty()) {
				TLog.severe(()->{return "The class "+mv.getClass().getSimpleName()
						+" must be setting as loadable, see "+TLoadable.class.getSimpleName();});
				TedrosContext.showMessage(new ModalMessage(iE.getString(TFxKey.MESSAGE_ERROR)));
			}else {
				if(l.size()==1) {
					TLoader r = l.get(0);
					if(!r.isLoadable()) {
						TLog.severe(()->{return r.getMessage();});
						TedrosContext.showMessage(new ModalMessage(iE.getString(TFxKey.MESSAGE_ERROR)));
					}else {
						r.loadInModule();
					}
				}else {
					VBox vb = new VBox(3);
					for(TLoader r : l) {
						if(!r.isLoadable()) {
							TLog.severe(()->{return r.getMessage();});
						}else {
							TModuleContext ctx = TedrosAppManager.getInstance()
							.getModuleContext(r.getModuleType());
							HBox hb = new HBox(3);
							TLabel lbl = new TLabel(ctx.getModuleDescriptor().getModuleName());
							TButton btn = new TButton(iE.getString(TFxKey.BUTTON_OPEN));
							btn.setOnAction(ev->{
								TedrosContext.hideModal();
								r.loadInModule();
							});
							hb.getChildren().addAll(lbl, btn);
							vb.getChildren().add(hb);
						}
					}
					
					if(vb.getChildren().isEmpty())
						TedrosContext.showMessage(new ModalMessage(iE.getString(TFxKey.MESSAGE_ERROR)));
					else
						TedrosContext.showModal(new TModalPane(vb));
				}
				
			}
		});
		return Arrays.asList(edit);
	}

}
