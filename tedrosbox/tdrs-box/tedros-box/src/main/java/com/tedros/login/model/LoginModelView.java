package com.tedros.login.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tedros.core.security.model.TUser;
import com.tedros.fxapi.annotation.control.TComboBoxField;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TPasswordField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.effect.TDropShadow;
import com.tedros.fxapi.annotation.effect.TEffect;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEntityProcess;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.builder.LanguageBuilder;
import com.tedros.fxapi.control.TText.TTextStyle;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.login.behavior.LoginBehavior;
import com.tedros.login.decorator.LoginDecorator;
import com.tedros.settings.security.model.TProfileModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.text.TextAlignment;


@TPresenter(modelClass=Login.class, behavior=@TBehavior(type=LoginBehavior.class), 
	decorator=@TDecorator(type=LoginDecorator.class, saveButtonText="#{tedros.validateUser}",
	viewTitle="#{tedros.login.view.title}"), 
	type = TDynaPresenter.class)
@TEntityProcess(entity=TUser.class, process=TLoginProcess.class)
public class LoginModelView extends TModelView<Login> {
	
	private SimpleLongProperty id;
	
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-fieldbox-title", 
			effect=@TEffect(dropShadow=@TDropShadow, parse = false), parse = true))
	@TText(text="#{tedros.login.form.title}", textAlignment=TextAlignment.LEFT, 
			textStyle=TTextStyle.LARGE)
	private SimpleStringProperty title;
	
	@TLabel(text = "#{tedros.login.user}")
	@TTextField(required=true, node=@TNode(requestFocus=true, parse = true))
	private SimpleStringProperty user;
	
	@TLabel(text = "#{tedros.login.password}")
	@TPasswordField(required=true)
	private SimpleStringProperty password;
	
	@TLabel(text = "#{tedros.language}")
	@TComboBoxField(items=LanguageBuilder.class, required=true)
	private SimpleStringProperty language;
	
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-fieldbox-info", 
			effect=@TEffect(dropShadow=@TDropShadow, parse = false), parse = true))
	@TText(text="#{tedros.profileText}", wrappingWidth=480, textAlignment=TextAlignment.LEFT, 
		textStyle=TTextStyle.MEDIUM)
	private SimpleStringProperty profileText;
	
	@TLabel(text = "#{tedros.profile}")
	@TComboBoxField(firstItemTex="#{tedros.select}",
		node=@TNode(disable=true, parse=true))
	private SimpleObjectProperty<TProfileModelView> profile;
	
	public LoginModelView() {
		super(new Login());
	}
	
	public LoginModelView(Login model) {
		super(model);
	} 
	
	public SimpleStringProperty getUser() {
		return user; 
	}

	public void setUser(SimpleStringProperty user) {
		this.user = user;
	}

	public SimpleStringProperty getPassword() {
		return password;
	}

	public void setPassword(SimpleStringProperty password) {
		this.password = password;
	}

	public SimpleStringProperty getLanguage() {
		return language;
	}

	public void setLanguage(SimpleStringProperty language) {
		this.language = language;
	}

	@Override
	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	@Override
	public SimpleLongProperty getId() {
		return id;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return user;
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}

	public SimpleStringProperty getTitle() {
		return title;
	}

	public void setTitle(SimpleStringProperty title) {
		this.title = title;
	}

	public SimpleObjectProperty<TProfileModelView> getProfile() {
		return profile;
	}

	public void setProfile(SimpleObjectProperty<TProfileModelView> profile) {
		this.profile = profile;
	}

	public SimpleStringProperty getProfileText() {
		return profileText;
	}

	public void setProfileText(SimpleStringProperty profileText) {
		this.profileText = profileText;
	}

}
