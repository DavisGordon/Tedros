/**
 * 
 */
package org.tedros.fx.builder;

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
import org.tedros.fx.control.TButton;
import org.tedros.fx.control.TLabel;
import org.tedros.fx.modal.TMessageBox;
import org.tedros.fx.presenter.model.TModelView;

import javafx.geometry.Pos;
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

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	@Override
	List<MenuItem> getMenuItems(TableView<M> table, TableRow<M> row) {
		TLanguage iE = TLanguage.getInstance();
		MenuItem edit = new MenuItem(iE.getString(TFxKey.BUTTON_EDIT));
		edit.setOnAction(e->{
			M mv = row.getItem();
			
			List<TLoader> l = TedrosModuleLoader.getInstance()
			.getLoader(mv.getModel());
			if(l.isEmpty()) {
				LOGGER.severe("The class "+mv.getClass().getSimpleName()
						+" must be setting as loadable, see "+TLoadable.class.getSimpleName());
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
		});
		return Arrays.asList(edit);
	}

}
