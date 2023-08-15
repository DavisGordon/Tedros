/**
 * 
 */
package org.tedros.fx.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.tedros.core.TLanguage;
import org.tedros.core.annotation.TView;
import org.tedros.core.context.TLoader;
import org.tedros.core.context.TViewDescriptor;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.message.TMessage;
import org.tedros.core.message.TMessageType;
import org.tedros.fx.TFxKey;
import org.tedros.fx.modal.TMessageBox;

/**
 * @author Davis Gordon
 *
 */
public class TRowFactoryCalbackHelper {

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	/**
	 * 
	 */
	private TRowFactoryCalbackHelper() {
	}
	
	/**
	 * @param iE
	 * @param mv
	 * @param l
	 */
	public static void callLoader(List<TLoader> l) {
		TLanguage iE = TLanguage.getInstance();
		if(l.isEmpty()) {
			LOGGER.severe("The model must be setting as loadable, see "+TView.class.getSimpleName());
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
				List<TMessage> msgs = new ArrayList<>();;
				for(TLoader r : l) {
					if(!r.isLoadable()) {
						LOGGER.severe(r.getMessage());
					}else {
						TViewDescriptor vds = TedrosAppManager.getInstance().getViewDescriptor(r.getModelViewType(), r.getModelType());
						String name = r.getModels().size()==1
								? iE.getFormatedString(TFxKey.MESSAGE_OPEN_ACTION, 
										r.getModels().get(0).toString(), vds.getTitle())
										: iE.getFormatedString(TFxKey.MESSAGE_LIST_ACTION, 
												r.getModels().size(), vds.getTitle());
						String btn = iE.getString(TFxKey.BUTTON_EXECUTE);
						msgs.add(new TMessage(name, btn, ev->{
							TedrosContext.hideModal();
							try {
								r.loadInModule();
							}catch(Exception ex) {
								LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
								TedrosContext.showMessage(new TMessage(TMessageType.ERROR, iE.getString(TFxKey.MESSAGE_ERROR)));
							}
						}));
					}
				}
				
				if(msgs.isEmpty())
					TedrosContext.showMessage(
						new TMessage(TMessageType.ERROR, iE.getString(TFxKey.MESSAGE_ERROR)));
				else
					TedrosContext.showModal( 
						new TMessageBox(iE.getString(TFxKey.MESSAGE_CHOOSE_ACTION), msgs));
			}
		}
	}

}
