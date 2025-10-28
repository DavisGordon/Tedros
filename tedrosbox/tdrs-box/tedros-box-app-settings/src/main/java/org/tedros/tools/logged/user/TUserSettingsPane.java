/**
 * 
 */
package org.tedros.tools.logged.user;

import org.tedros.core.context.TedrosContext;
import org.tedros.core.security.model.TUser;
import org.tedros.fx.presenter.dynamic.view.TDynaView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.StackPane;

/**
 * @author Davis Gordon
 *
 */
public class TUserSettingsPane extends StackPane {

	public TUserSettingsPane() {
		// 1) The model, note: TUser is an ITEntity
		TUser usr = TedrosContext.getLoggedUser();
		// 2) The model view, please note: here are the form and view settings
		TUserSettingModelView umv = new TUserSettingModelView(usr);
		ObservableList<TUserSettingModelView> l = FXCollections.observableArrayList(umv);
		// 3) The View, note: we instantiate it but who decorates and behaves it is the
		// presenter in it.
		TDynaView<TUserSettingModelView> v = new TDynaView<>(TUserSettingModelView.class, l, false);
		v.tLoad();
		v.setMinHeight(420);
		super.getChildren().add(v);
	}
}
