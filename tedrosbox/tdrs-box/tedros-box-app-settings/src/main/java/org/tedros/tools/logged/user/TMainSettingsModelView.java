/**
 * 
 */
package org.tedros.tools.logged.user;

import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.THyperlinkField;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.scene.control.TButtonBase;
import org.tedros.fx.annotation.scene.control.TLabeled;
import org.tedros.fx.annotation.text.TFont;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.entity.decorator.TSaveViewDecorator;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.text.FontWeight;

/**
 * @author Davis Gordon
 *
 */

@TPresenter(type=TDynaPresenter.class, 
			decorator=@TDecorator(type = TSaveViewDecorator.class, 
			buildModesRadioButton=false, saveButtonText=TUsualKey.EXIT ),
			behavior=@TBehavior(type=TMainSettingsBehavior.class, saveOnlyChangedModels=false))
public class TMainSettingsModelView extends TModelView<MainSettings> {

	@THyperlinkField(labeled=@TLabeled(text=TUsualKey.CLEAR_HISTORY, 
			font=@TFont(size=4, weight=FontWeight.BOLD), parse = true),
			buttonBase=@TButtonBase(onAction=ClearHistoryEventBuilder.class))
	private SimpleStringProperty clearHistory;
	
	@THyperlinkField(labeled=@TLabeled(text=TUsualKey.LOGOUT, 
			font=@TFont(size=4, weight=FontWeight.BOLD), parse = true),
			buttonBase=@TButtonBase(onAction=LogoutEventBuilder.class))
	private SimpleStringProperty logout;
	
	
	public TMainSettingsModelView(MainSettings model) {
		super(model);
	}

	@Override
	public SimpleStringProperty toStringProperty() {
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
	 * @return the clearHistory
	 */
	public SimpleStringProperty getClearHistory() {
		return clearHistory;
	}

	/**
	 * @param clearHistory the clearHistory to set
	 */
	public void setClearHistory(SimpleStringProperty clearHistory) {
		this.clearHistory = clearHistory;
	}

}
