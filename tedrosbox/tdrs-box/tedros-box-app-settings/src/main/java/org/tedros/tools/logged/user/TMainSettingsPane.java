/**
 * 
 */
package org.tedros.tools.logged.user;

import org.tedros.api.presenter.view.TDetachViewType;
import org.tedros.fx.presenter.dynamic.view.TDynaView;

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
	public TMainSettingsPane() {
		MainSettings usr = new MainSettings();
    	usr.setLogout("logout");
		TMainSettingsModelView umv = new TMainSettingsModelView(usr);
		ObservableList<TMainSettingsModelView> l = FXCollections.observableArrayList(umv);
    	TDynaView<TMainSettingsModelView> v = new TDynaView<>(TMainSettingsModelView.class, l, false, TDetachViewType.NONE);
    	v.tLoad();
    	this.getStyleClass().add("custom-popup");
		super.getChildren().add(v);
				
		
	}

	

}
