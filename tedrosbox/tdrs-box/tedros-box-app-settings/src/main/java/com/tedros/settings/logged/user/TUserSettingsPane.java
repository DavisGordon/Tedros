/**
 * 
 */
package com.tedros.settings.logged.user;

import com.tedros.core.context.TedrosContext;
import com.tedros.core.security.model.TUser;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.StackPane;

/**
 * @author Davis Gordon
 *
 */
public class TUserSettingsPane extends StackPane {

	/**
	 * 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public TUserSettingsPane() {
		TUser usr = TedrosContext.getLoggedUser();
    	
		TUserModelView umv = new TUserModelView(usr);
    	ObservableList l = FXCollections.observableArrayList(umv);
    	TDynaView<TUserModelView> v = new TDynaView<>(TUserModelView.class, l, false);
    	v.tLoad();
    	v.setMinHeight(400);
		super.getChildren().add(v);
				
		
	}

	

}
