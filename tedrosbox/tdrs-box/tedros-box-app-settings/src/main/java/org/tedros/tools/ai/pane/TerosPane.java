/**
 * 
 */
package org.tedros.tools.ai.pane;

import org.tedros.api.presenter.view.TDetachViewType;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.core.context.TSecurityDescriptor;
import org.tedros.core.context.TedrosContext;
import org.tedros.fx.modal.TMessageBox;
import org.tedros.fx.presenter.dynamic.view.TDynaView;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.ai.model.TerosMV;

import javafx.scene.layout.StackPane;

/**
 * @author Davis Gordon
 *
 */
public class TerosPane extends StackPane {

	/**
	 * 
	 */
	public TerosPane() {
		
		TSecurity tSecurity = TerosMV.class.getAnnotation(TSecurity.class);
		TSecurityDescriptor descriptor = new TSecurityDescriptor(tSecurity);
		if(TedrosContext.isUserAuthorized(descriptor, TAuthorizationType.VIEW_ACCESS)) {		
			TDynaView<TerosMV> v = new TDynaView<>(TerosMV.class, TDetachViewType.NONE);
	    	v.tLoad();
	    	v.setMinHeight(400);
			super.getChildren().add(v);
		}else {
			TMessageBox box = new TMessageBox(ToolsKey.MESSAGE_PERMISSION_DENIED);
			super.getChildren().add(box);
		}
				
		
	}

	

}
