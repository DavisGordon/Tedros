/**
 * 
 */
package org.tedros.tools.logged.user;

import org.tedros.core.context.TedrosContext;
import org.tedros.core.security.model.TProfile;
import org.tedros.core.security.model.TUser;
import org.tedros.fx.annotation.TObservableValue;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TPasswordField;
import org.tedros.fx.annotation.control.TRadioButton;
import org.tedros.fx.annotation.control.TShowField;
import org.tedros.fx.annotation.control.TShowField.TField;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.control.TVerticalRadioGroup;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.property.TReadOnlyBooleanProperty;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.text.TFont;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.tools.ToolsKey;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
@TForm(name=ToolsKey.SECURITY_USER_FORM_NAME)
@TPresenter(type=TDynaPresenter.class, 
			decorator=@TDecorator(type = TUserSettingDecorator.class, 
			buildModesRadioButton=false, saveButtonText=ToolsKey.APPLY ),
			behavior=@TBehavior(type=TUserSettingBehavior.class, saveOnlyChangedModels=false))
@TEjbService(model=TUser.class, serviceName = "TUserControllerRemote")
public class TUserSettingModelView extends TEntityModelView<TUser> {

	@TLabel(text=ToolsKey.NAME, font=@TFont(size=10))
	@TTextField(maxLength=100, required=true)
	private SimpleStringProperty name;
	
	
	@TLabel(text=ToolsKey.USERLOGIN, font=@TFont(size=10))
	@TTextField(maxLength=100, required=true)
	private SimpleStringProperty login;
	
	@TLabel(text=ToolsKey.PASSWORD, font=@TFont(size=10))
	@TPasswordField(required=true,
		node=@TNode(focusedProperty=@TReadOnlyBooleanProperty(
				observableValue=@TObservableValue(addListener=TEncriptPasswordChangeListener.class), 
				parse = true), 
		parse = true))
	private SimpleStringProperty password;
	
	@TLabel(text = "#{tedros.profile}", font=@TFont(size=10))
	@TShowField(fields=@TField(name="name"))
	private SimpleObjectProperty<TProfile> activeProfile;

	@TLabel(text = "#{tedros.language}", font=@TFont(size=10))
	@TVerticalRadioGroup(radioButtons= {@TRadioButton(text = "English", userData = "en"),
			@TRadioButton(text = "Português", userData = "pt")})
	private SimpleStringProperty language;
	
	
	private SimpleStringProperty lastPassword;
	
	public TUserSettingModelView(TUser entity) {
		super(entity);
		copyPassword();
		language.setValue(TedrosContext.getLocale().getLanguage());
	}

	private void copyPassword() {
		if(password!=null && password.getValue()!=null)
			lastPassword.setValue(password.getValue());
	}
	
	@Override
	public void reload(TUser model) {
		super.reload(model);
		copyPassword();
		language.setValue(TedrosContext.getLocale().getLanguage());
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.presenter.model.TModelView#toStringProperty()
	 */
	@Override
	public SimpleStringProperty toStringProperty() {
		return name;
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

	public SimpleStringProperty getLogin() {
		return login;
	}

	public void setLogin(SimpleStringProperty login) {
		this.login = login;
	}

	public SimpleStringProperty getPassword() {
		return password;
	}

	public void setPassword(SimpleStringProperty password) {
		this.password = password;
	}
	
	public SimpleStringProperty getLastPassword() {
		return lastPassword;
	}

	public void setLastPassword(SimpleStringProperty lastPassword) {
		this.lastPassword = lastPassword;
	}

	/**
	 * @return the language
	 */
	public SimpleStringProperty getLanguage() {
		return language;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(SimpleStringProperty language) {
		this.language = language;
	}

	/**
	 * @return the activeProfile
	 */
	public SimpleObjectProperty<TProfile> getActiveProfile() {
		return activeProfile;
	}

	/**
	 * @param activeProfile the activeProfile to set
	 */
	public void setActiveProfile(SimpleObjectProperty<TProfile> activeProfile) {
		this.activeProfile = activeProfile;
	}


}
