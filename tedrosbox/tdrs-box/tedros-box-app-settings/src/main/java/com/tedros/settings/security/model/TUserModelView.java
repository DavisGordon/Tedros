/**
 * 
 */
package com.tedros.settings.security.model;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.core.security.model.TProfile;
import com.tedros.core.security.model.TUser;
import com.tedros.fxapi.annotation.TObservableValue;
import com.tedros.fxapi.annotation.control.TCheckBoxField;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewType;
import com.tedros.fxapi.annotation.control.TOptionsList;
import com.tedros.fxapi.annotation.control.TPasswordField;
import com.tedros.fxapi.annotation.control.TPickListField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.TAccordion;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.layout.TTitledPane;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.property.TReadOnlyBooleanProperty;
import com.tedros.fxapi.annotation.reader.TColumnReader;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.reader.TTableReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TLabeled;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.control.TText.TTextStyle;
import com.tedros.fxapi.domain.THtmlConstant;
import com.tedros.fxapi.domain.TLabelPosition;
import com.tedros.fxapi.domain.TStyleParameter;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.entity.behavior.TMasterCrudViewBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TMasterCrudViewDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.settings.security.action.TEncriptPasswordChangeListener;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

/**
 * @author Davis Gordon
 *
 */
@TForm(name="#{security.user.form.name}", showBreadcrumBar=true, editCssId="")
@TFormReaderHtml
@TEjbService(serviceName = "TUserControllerRemote", model=TUser.class)
@TPresenter(type=TDynaPresenter.class, 
			decorator=@TDecorator(type = TMasterCrudViewDecorator.class, viewTitle="#{security.user.view.title}", listTitle="#{security.user.list.title}"),
			behavior=@TBehavior(type=TMasterCrudViewBehavior.class))
@TSecurity(	id="T_CUSTOM_SECURITY_USER", 
			appName="#{settings.app.name}", 
			moduleName="#{label.user}", 
			viewName="#{security.user.view.title}",
			allowedAccesses={	TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
			   					TAuthorizationType.NEW, TAuthorizationType.SAVE, TAuthorizationType.DELETE})
public class TUserModelView extends TEntityModelView<TUser> {

	private SimpleLongProperty id;
	
	@TAccordion(expandedPane="main", node=@TNode(id="UserAcc",parse = true),
			panes={
					@TTitledPane(text="Principal", node=@TNode(id="main",parse = true), expanded=true,
							fields={"header", "name", "login", "active"}),
					@TTitledPane(text="Detalhe", node=@TNode(id="detail",parse = true),
						fields={"profilesText","profiles"})})
	@TReaderHtml(htmlTemplateForControlValue="<h2 style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
				cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
				cssForHtmlBox="", cssForContentValue="")
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-pane", parse = true))
	@TText(text="#{label.user.header}", textAlignment=TextAlignment.LEFT, 
			textStyle = TTextStyle.LARGE)
	private SimpleStringProperty header;
	
	@TReaderHtml
	@TLabel(text="#{label.name}:")
	@TTextField(maxLength=100, required=true)
	private SimpleStringProperty name;
	
	@TReaderHtml
	@TLabel(text="#{label.userLogin}:")
	@TTextField(maxLength=100, required=true)
	@THBox(	pane=@TPane(children={"login","password"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="login", priority=Priority.NEVER), 
		   		@TPriority(field="password", priority=Priority.NEVER)}))
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
	@TCheckBoxField(labeled=@TLabeled(text="#{label.active}", parse = true))
	private SimpleBooleanProperty active;
	
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", parse = true))
	@TText(text="#{label.profilesText}", textAlignment=TextAlignment.LEFT, 
			textStyle = TTextStyle.LARGE, mode=TViewMode.EDIT)
	private SimpleStringProperty profilesText;
	
	@TTableReaderHtml(label=@TLabel(text="#{label.profiles}:"), 
		column = { 	@TColumnReader(field = "name", name = "Name"), 
					@TColumnReader(field = "description", name = "Description")})
	@TPickListField(selectedLabel="#{label.selected}", 
			sourceLabel="#{label.profiles}", required=true,
			optionsList=@TOptionsList(entityClass=TProfile.class,
						optionModelViewClass=TProfileModelView.class, serviceName = "TProfileControllerRemote"))
	@TModelViewType(modelClass=TProfile.class, modelViewClass=TProfileModelView.class, required=true)
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
	
	

	public SimpleStringProperty getLastPassword() {
		return lastPassword;
	}

	public void setLastPassword(SimpleStringProperty lastPassword) {
		this.lastPassword = lastPassword;
	}
}
