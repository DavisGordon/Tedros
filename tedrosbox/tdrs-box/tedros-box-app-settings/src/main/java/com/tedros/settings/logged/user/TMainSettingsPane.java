/**
 * 
 */
package com.tedros.settings.logged.user;

import com.tedros.core.context.TedrosContext;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.StackPane;

/**
 * @author Davis Gordon
 *
 */
public class TMainSettingsPane extends StackPane {

	/**
	 * 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public TMainSettingsPane() {
		MainSettings usr = new MainSettings();
    	//usr.setCollapseMenu(TedrosContext.isCollapseMenu());
    	usr.setLogout("logout");
		TMainSettingsModelView umv = new TMainSettingsModelView(usr);
		umv.getCollapseMenu().setValue(TedrosContext.isCollapseMenu());
    	ObservableList l = FXCollections.observableArrayList(umv);
    	TDynaView<TMainSettingsModelView> v = new TDynaView<>(TMainSettingsModelView.class, l, false);
    	v.tLoad();
    	v.setMinHeight(100);
		super.getChildren().add(v);
				
		
	}

	

}
