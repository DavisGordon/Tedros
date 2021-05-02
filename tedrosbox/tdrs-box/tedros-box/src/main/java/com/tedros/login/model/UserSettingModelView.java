package com.tedros.login.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tedros.core.security.model.TProfile;
import com.tedros.core.security.model.TUser;
import com.tedros.fxapi.annotation.control.TComboBoxField;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewCollectionType;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEntityProcess;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.login.LanguageBuilder;
import com.tedros.login.behavior.UserSettingBehavior;
import com.tedros.login.decorator.UserSettingDecorator;
import com.tedros.settings.security.model.TProfileModelView;
import com.tedros.settings.security.process.TUserProcess;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;


@TPresenter(modelClass=UserSetting.class, behavior=@TBehavior(type=UserSettingBehavior.class), 
	decorator=@TDecorator(type=UserSettingDecorator.class, saveButtonText="#{tedros.validateUser}", viewTitle="#{tedros.login.view.title}"), 
	type = TDynaPresenter.class)
@TEntityProcess(entity=TUser.class, process=TUserProcess.class)
public class UserSettingModelView extends TModelView<UserSetting> {
	
	private SimpleLongProperty id;
	
	@TLabel(text = "#{tedros.language}")
	@TComboBoxField(items=LanguageBuilder.class, required=true)
	private SimpleStringProperty language;
	
	
	@TLabel(text = "#{tedros.profile}")
	@TComboBoxField(firstItemTex="#{tedros.select}")
	private SimpleObjectProperty<TProfileModelView> profile;
	
	/*@TModelViewCollectionType(modelClass=TProfile.class, modelViewClass=TProfileModelView.class, required=true)
	private ITObservableList<TProfileModelView> profiles;*/
	
	
	public UserSettingModelView(UserSetting model) {
		super(model);
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
		return language;
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}


	public SimpleObjectProperty<TProfileModelView> getProfile() {
		return profile;
	}

	public void setProfile(SimpleObjectProperty<TProfileModelView> profile) {
		this.profile = profile;
	}

}
