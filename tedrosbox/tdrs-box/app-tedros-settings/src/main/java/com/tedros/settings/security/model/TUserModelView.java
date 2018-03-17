/**
 * 
 */
package com.tedros.settings.security.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.core.security.model.TProfile;
import com.tedros.core.security.model.TUser;
import com.tedros.fxapi.annotation.TObservableValue;
import com.tedros.fxapi.annotation.control.TCheckBoxField;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TLabelPosition;
import com.tedros.fxapi.annotation.control.TModelViewCollectionType;
import com.tedros.fxapi.annotation.control.TOptionsList;
import com.tedros.fxapi.annotation.control.TPasswordField;
import com.tedros.fxapi.annotation.control.TPickListField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEntityProcess;
import com.tedros.fxapi.annotation.property.TReadOnlyBooleanProperty;
import com.tedros.fxapi.annotation.reader.TColumnReader;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.reader.TTableReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.text.TFont;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.domain.THtmlConstant;
import com.tedros.fxapi.domain.TStyleParameter;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.entity.behavior.TMainCrudViewWithListViewBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TMainCrudViewWithListViewDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.settings.security.action.TEncriptPasswordChangeListener;
import com.tedros.settings.security.process.LoadTProfileOptionListProcess;
import com.tedros.settings.security.process.TUserProcess;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.text.TextAlignment;

/**
 * @author Davis Gordon
 *
 */
@TForm(name="#{security.user.form.name}")
@TFormReaderHtml
@TPresenter(type=TDynaPresenter.class, 
			decorator=@TDecorator(type = TMainCrudViewWithListViewDecorator.class, viewTitle="#{security.user.view.title}", listTitle="#{security.user.list.title}"),
			behavior=@TBehavior(type=TMainCrudViewWithListViewBehavior.class))
@TEntityProcess(entity=TUser.class, process = TUserProcess.class)
@TSecurity(	id="T_CUSTOM_SECURITY_USER", 
			appName="#{settings.app.name}", 
			moduleName="#{label.user}", 
			viewName="#{security.user.view.title}",
			allowedAccesses={	TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
			   					TAuthorizationType.NEW, TAuthorizationType.SAVE, TAuthorizationType.DELETE})
public class TUserModelView extends TEntityModelView<TUser> {

	private SimpleLongProperty id;
	
	@TReaderHtml(htmlTemplateForControlValue="<h2 style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
				cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
				cssForHtmlBox="", cssForContentValue="")
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-pane", parse = true))
	@TText(text="#{label.user.header}", font=@TFont(size=22), textAlignment=TextAlignment.LEFT, 
		node=@TNode(id="t-form-title-text", parse = true))
	private SimpleStringProperty header;
	
	@TReaderHtml
	@TLabel(text="#{label.name}:")
	@TTextField(maxLength=100, required=true)
	private SimpleStringProperty name;
	
	@TReaderHtml
	@TLabel(text="#{label.userLogin}:")
	@TTextField(maxLength=100, required=true)
	private SimpleStringProperty login;
	
	@TLabel(text="#{label.password}:")
	@TPasswordField(required=true, 
					node=@TNode(focusedProperty=@TReadOnlyBooleanProperty(
												observableValue=@TObservableValue(addListener=TEncriptPasswordChangeListener.class), 
												parse = true), 
								parse = true))
	private SimpleStringProperty password;
	
	@TReaderHtml
	@TLabel(text="#{label.active}:", position=TLabelPosition.LEFT)
	@TCheckBoxField(label=@TLabel(text="#{label.active}"))
	private SimpleBooleanProperty active;
	
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", parse = true))
	@TText(text="#{label.profilesText}", font=@TFont(size=22), textAlignment=TextAlignment.LEFT, 
		node=@TNode(id="t-form-title-text", parse = true), mode=TViewMode.EDIT)
	private SimpleStringProperty profilesText;
	
	@TTableReaderHtml(label=@TLabel(text="#{label.profiles}:"), 
		column = { 	@TColumnReader(field = "name", name = "Name"), 
					@TColumnReader(field = "description", name = "Description")})
	@TPickListField(selectedLabel="#{label.selected}", 
			sourceLabel="#{label.profiles}", required=true,
			optionsList=@TOptionsList(entityClass=TProfile.class,
						optionModelViewClass=TProfileModelView.class, 
						optionsProcessClass=LoadTProfileOptionListProcess.class))
	@TModelViewCollectionType(entityClass=TProfile.class, modelViewClass=TProfileModelView.class, required=true)
	private ITObservableList<TProfileModelView> profiles;
	
	private SimpleStringProperty lastPassword;
	
	public TUserModelView(TUser entity) {
		super(entity);
		copyPassword();
		
		/*TimelineBuilder.create().keyFrames(
	            new KeyFrame(Duration.millis(20000), 
	                new EventHandler<ActionEvent>() {
	                    public void handle(ActionEvent t) {
	                    	profiles.add(new TProfileModelView(new TProfile(RandomStringUtils.randomAlphanumeric(4), RandomStringUtils.randomAlphanumeric(12))));
	                    }
	                })
	        )
	        .cycleCount(Animation.INDEFINITE)
	        .build()
	        .play();*/
	}

	private void copyPassword() {
		if(password!=null && password.getValue()!=null)
			lastPassword.setValue(password.getValue());
	}
	
	@Override
	public void reload(TUser model) {
		super.reload(model);
		copyPassword();
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
	
	public ITObservableList<TProfileModelView> getProfiles() {
		return profiles;
	}

	public void setProfiles(ITObservableList<TProfileModelView> profiles) {
		this.profiles = profiles;
	}

	public SimpleBooleanProperty getActive() {
		return active;
	}

	public void setActive(SimpleBooleanProperty active) {
		this.active = active;
	}

	public SimpleStringProperty getHeader() {
		return header;
	}

	public void setHeader(SimpleStringProperty header) {
		this.header = header;
	}

	public SimpleStringProperty getProfilesText() {
		return profilesText;
	}

	public void setProfilesText(SimpleStringProperty profilesText) {
		this.profilesText = profilesText;
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}

	public SimpleStringProperty getLastPassword() {
		return lastPassword;
	}

	public void setLastPassword(SimpleStringProperty lastPassword) {
		this.lastPassword = lastPassword;
	}
}
