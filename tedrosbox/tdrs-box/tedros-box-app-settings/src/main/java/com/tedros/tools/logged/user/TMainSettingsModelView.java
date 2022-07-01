/**
 * 
 */
package com.tedros.tools.logged.user;

import com.tedros.fxapi.annotation.control.THyperlinkField;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.scene.control.TButtonBase;
import com.tedros.fxapi.annotation.scene.control.TLabeled;
import com.tedros.fxapi.annotation.text.TFont;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.entity.decorator.TSaveViewDecorator;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.tools.ToolsKey;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.text.FontWeight;

/**
 * @author Davis Gordon
 *
 */

@TPresenter(type=TDynaPresenter.class, 
			decorator=@TDecorator(type = TSaveViewDecorator.class, 
			buildModesRadioButton=false, saveButtonText=ToolsKey.EXIT ),
			behavior=@TBehavior(type=TMainSettingsBehavior.class, saveOnlyChangedModels=false))
public class TMainSettingsModelView extends TModelView<MainSettings> {

	private SimpleLongProperty id;
	
	@THyperlinkField(labeled=@TLabeled(text=ToolsKey.LOGOUT, 
			font=@TFont(size=4, weight=FontWeight.BOLD), parse = true),
			buttonBase=@TButtonBase(onAction=LogoutEventBuilder.class))
	private SimpleStringProperty logout;
	
	
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

}
