/**
 * 
 */
package com.tedros.settings.logged.user;

import com.tedros.fxapi.annotation.control.TCheckBoxField;
import com.tedros.fxapi.annotation.control.THyperlinkField;
import com.tedros.fxapi.annotation.control.TTrigger;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.scene.control.TButtonBase;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.scene.control.TLabeled;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.entity.decorator.TSaveViewDecorator;
import com.tedros.fxapi.presenter.model.TModelView;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */

@TPresenter(type=TDynaPresenter.class, 
			decorator=@TDecorator(type = TSaveViewDecorator.class, 
			buildModesRadioButton=false, saveButtonText="#{label.exit}" ),
			behavior=@TBehavior(type=TMainSettingsBehavior.class, saveOnlyChangedModels=false))
public class TMainSettingsModelView extends TModelView<MainSettings> {

	private SimpleLongProperty id;
	
	@THyperlinkField(labeled=@TLabeled(text="#{label.logout}", parse = true),
			buttonBase=@TButtonBase(onAction=LogoutEventBuilder.class))
	private SimpleStringProperty logout;
	
	@TCheckBoxField(labeled=@TLabeled(text="#{label.collapse.menu}", parse = true), 
			control=@TControl(tooltip="#{label.collapse.menu.tooltip}", parse = true))
	@TTrigger(triggerClass=CollapseMenuTrigger.class, mode=TViewMode.EDIT)
	private SimpleBooleanProperty collapseMenu;
	
	public TMainSettingsModelView(MainSettings model) {
		super(model);
	}

	@Override
	public void setId(SimpleLongProperty id) {
		this.id=id;
	}

	@Override
	public SimpleLongProperty getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return logout;
	}

	/**
	 * @return the logout
	 */
	public SimpleStringProperty getLogout() {
		return logout;
	}

	/**
	 * @param logout the logout to set
	 */
	public void setLogout(SimpleStringProperty logout) {
		this.logout = logout;
	}

	/**
	 * @return the collapseMenu
	 */
	public SimpleBooleanProperty getCollapseMenu() {
		return collapseMenu;
	}

	/**
	 * @param collapseMenu the collapseMenu to set
	 */
	public void setCollapseMenu(SimpleBooleanProperty collapseMenu) {
		this.collapseMenu = collapseMenu;
	}

}
