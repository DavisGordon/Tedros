/**
 * 
 */
package com.tedros.tools.logged.user;

import com.tedros.core.context.TedrosContext;
import com.tedros.core.security.model.TProfile;
import com.tedros.core.security.model.TUser;
import com.tedros.fxapi.annotation.TObservableValue;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TPasswordField;
import com.tedros.fxapi.annotation.control.TRadioButton;
import com.tedros.fxapi.annotation.control.TShowField;
import com.tedros.fxapi.annotation.control.TShowField.TField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TVerticalRadioGroup;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.property.TReadOnlyBooleanProperty;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.text.TFont;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.tools.util.ToolsKey;

import javafx.beans.property.SimpleLongProperty;
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

	private SimpleLongProperty id;
	
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
	 * @see com.tedros.fxapi.presenter.model.TModelView#setId(javafx.beans.property.SimpleLongProperty)
	 */
	@Override
	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.model.TModelView#getId()
	 */
	@Override
	public SimpleLongProperty getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.model.TModelView#getDisplayProperty()
	 */
	@Override
	public SimpleStringProperty getDisplayProperty() {
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
