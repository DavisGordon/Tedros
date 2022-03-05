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
    	
		TUserSettingModelView umv = new TUserSettingModelView(usr);
    	ObservableList l = FXCollections.observableArrayList(umv);
    	TDynaView<TUserSettingModelView> v = new TDynaView<>(TUserSettingModelView.class, l, false);
    	v.tLoad();
    	v.setMinHeight(420);
		super.getChildren().add(v);
				
		
	}

	

}
