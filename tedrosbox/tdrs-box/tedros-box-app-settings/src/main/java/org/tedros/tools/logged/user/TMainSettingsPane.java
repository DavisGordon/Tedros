/**
 * 
 */
package org.tedros.tools.logged.user;

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
    	//usr.setCollapseMenu(TedrosContext.isCollapseMenu());
    	usr.setLogout("logout");
		TMainSettingsModelView umv = new TMainSettingsModelView(usr);
		//umv.getCollapseMenu().setValue(TedrosContext.isCollapseMenu());
    	ObservableList<TMainSettingsModelView> l = FXCollections.observableArrayList(umv);
    	TDynaView<TMainSettingsModelView> v = new TDynaView<>(TMainSettingsModelView.class, l, false);
    	v.tLoad();
    	v.setMinHeight(60);
		super.getChildren().add(v);
				
		
	}

	

}
