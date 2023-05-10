/**
 * 
 */
package org.tedros.tools.module.user.model;

import org.tedros.api.presenter.view.TViewMode;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.core.controller.TProfileController;
import org.tedros.core.controller.TUserController;
import org.tedros.core.domain.DomainApp;
import org.tedros.core.security.model.TProfile;
import org.tedros.core.security.model.TUser;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.TObservableValue;
import org.tedros.fx.annotation.control.TCheckBoxField;
import org.tedros.fx.annotation.control.TContent;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TModelViewType;
import org.tedros.fx.annotation.control.TOptionsList;
import org.tedros.fx.annotation.control.TPasswordField;
import org.tedros.fx.annotation.control.TPickListField;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.form.TDetailForm;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.property.TReadOnlyBooleanProperty;
import org.tedros.fx.annotation.reader.TFormReaderHtml;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TLabeled;
import org.tedros.fx.annotation.text.TText;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.domain.TLabelPosition;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.entity.behavior.TMasterCrudViewBehavior;
import org.tedros.fx.presenter.entity.decorator.TMasterCrudViewDecorator;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.module.user.action.TEncriptPasswordChangeListener;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

/**
 * @author Davis Gordon
 *
 */
@TFormReaderHtml
@TForm(name=ToolsKey.SECURITY_USER_FORM_NAME, showBreadcrumBar=true, editCssId="")
@TEjbService(serviceName = TUserController.JNDI_NAME, model=TUser.class)
@TPresenter(type=TDynaPresenter.class, 
			decorator=@TDecorator(type = TMasterCrudViewDecorator.class, 
			viewTitle=ToolsKey.VIEW_USER, listTitle=ToolsKey.SECURITY_USER_LIST_TITLE),
			behavior=@TBehavior(type=TMasterCrudViewBehavior.class))
@TSecurity(	id=DomainApp.USER_FORM_ID, 
			appName=ToolsKey.APP_TOOLS, 
			moduleName=ToolsKey.MODULE_USER, 
			viewName=ToolsKey.VIEW_USER,
			allowedAccesses={	TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
			   					TAuthorizationType.NEW, TAuthorizationType.SAVE, TAuthorizationType.DELETE})
public class TUserMV extends TEntityModelView<TUser> {
	
	@TTabPane(tabs = { 
			@TTab(closable=false, scroll=false, content = @TContent(detailForm=
				@TDetailForm(fields={"header", "name", "login", "active"})), 
				text = TUsualKey.MAIN), 
			@TTab(closable=false, content = @TContent(detailForm=
				@TDetailForm(fields={"profilesText","profiles"})), 
				text = TUsualKey.DETAIL)
	})
	
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id=TFieldBox.INFO, parse = true))
	@TText(text=ToolsKey.TEXT_USER_HEADER, textAlignment=TextAlignment.LEFT, 
			textStyle = TTextStyle.LARGE)
	private SimpleStringProperty header;
	
	@TLabel(text=TUsualKey.NAME)
	@TTextField(maxLength=100, required=true)
	private SimpleStringProperty name;
	
	@TLabel(text=TUsualKey.LOGIN)
	@TTextField(maxLength=100, required=true)
	@THBox(	pane=@TPane(children={"login","password"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="login", priority=Priority.NEVER), 
		   		@TPriority(field="password", priority=Priority.NEVER)}))
	private SimpleStringProperty login;
	
	@TLabel(text=TUsualKey.PASSWORD)
	@TPasswordField(required=true, 
		node=@TNode(focusedProperty=@TReadOnlyBooleanProperty(
				observableValue=@TObservableValue(addListener=TEncriptPasswordChangeListener.class), 
				parse = true), 
		parse = true))
	private SimpleStringProperty password;
	
	@TLabel(text=TUsualKey.ACTIVE, position=TLabelPosition.LEFT)
	@TCheckBoxField(labeled=@TLabeled(text=TUsualKey.YES, parse = true))
	private SimpleBooleanProperty active;
	
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id=TFieldBox.INFO, parse = true))
	@TText(text=ToolsKey.TEXT_PROFILE_HEADER, textAlignment=TextAlignment.LEFT, 
			textStyle = TTextStyle.LARGE, mode=TViewMode.EDIT)
	private SimpleStringProperty profilesText;
	
	@TPickListField(selectedLabel=TUsualKey.SELECTED, 
			sourceLabel=TUsualKey.PROFILES, required=true,
			optionsList=@TOptionsList(entityClass=TProfile.class,
						optionModelViewClass=TProfileMV.class, serviceName = TProfileController.JNDI_NAME))
	@TModelViewType(modelClass=TProfile.class, modelViewClass=TProfileMV.class)
	private ITObservableList<TProfileMV> profiles;
	
	private SimpleStringProperty lastPassword;
	
	public TUserMV(TUser entity) {
		super(entity);
		copyPassword();
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
	 * @see org.tedros.fx.presenter.model.TModelView#setId(javafx.beans.property.SimpleLongProperty)
	 */

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
	
	public ITObservableList<TProfileMV> getProfiles() {
		return profiles;
	}

	public void setProfiles(ITObservableList<TProfileMV> profiles) {
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
	
	

	public SimpleStringProperty getLastPassword() {
		return lastPassword;
	}

	public void setLastPassword(SimpleStringProperty lastPassword) {
		this.lastPassword = lastPassword;
	}
}
