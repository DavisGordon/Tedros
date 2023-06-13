package org.tedros.login.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.tedros.core.security.model.TUser;
import org.tedros.fx.annotation.control.TComboBoxField;
import org.tedros.fx.annotation.control.TContent;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TPasswordField;
import org.tedros.fx.annotation.control.TRadio;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.control.TTextInputControl;
import org.tedros.fx.annotation.control.TVRadioGroup;
import org.tedros.fx.annotation.effect.TDropShadow;
import org.tedros.fx.annotation.effect.TEffect;
import org.tedros.fx.annotation.form.TDetailForm;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEntityProcess;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.annotation.text.TText;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.login.behavior.LoginBehavior;
import org.tedros.login.decorator.LoginDecorator;
import org.tedros.tools.module.user.model.TProfileMV;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

@TForm(name = "#{tedros.login.view.title}", scroll=false)
@TPresenter(modelClass=Login.class, behavior=@TBehavior(type=LoginBehavior.class), 
	decorator=@TDecorator(type=LoginDecorator.class, saveButtonText="#{tedros.validateUser}",
	viewTitle="#{tedros.login.view.title}"), 
	type = TDynaPresenter.class)
@TEntityProcess(entity=TUser.class, process=TLoginProcess.class)
public class LoginModelView extends TModelView<Login> {
	
	@TTabPane(tabs = { 
			@TTab(closable=false, scroll=false, content = @TContent(detailForm=
				@TDetailForm(fields={"title", "name", "user", "profileText","profile"})), 
				text = "#{tedros.log.in}"), 
			@TTab(closable=false, content = @TContent(detailForm=
				@TDetailForm(fields={"header", "url", "serverIp","theme",
						"language"})), 
				text = "#{tedros.config}")
	})
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-fieldbox-title", 
			effect=@TEffect(dropShadow=@TDropShadow, parse = false), parse = true))
	@TText(text="#{tedros.login.form.title}", textAlignment=TextAlignment.LEFT, 
			textStyle=TTextStyle.LARGE)
	private SimpleStringProperty title;
	
	@TLabel(text = "#{tedros.login.name}")
	@TTextField()
	private SimpleStringProperty name;
	
	@TLabel(text = "#{tedros.login.user}")
	@TTextField(required=true, node=@TNode(requestFocus=true, parse = true))
	@THBox(pane=@TPane(children= {"user", "password"}), spacing=10, fillHeight=true,
			hgrow=@THGrow(priority={@TPriority(field="user", priority=Priority.ALWAYS), 
				   		@TPriority(field="password", priority=Priority.ALWAYS) }))
	private SimpleStringProperty user;
	
	@TLabel(text = "#{tedros.login.password}")
	@TPasswordField(required=true)
	private SimpleStringProperty password;
	
	@TLabel(text = "#{tedros.language}")
	@TVRadioGroup(radio= {@TRadio(text = "English", userData = "en"),
			@TRadio(text = "Português", userData = "pt")})
	private SimpleStringProperty language;
	
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-fieldbox-info", 
			effect=@TEffect(dropShadow=@TDropShadow, parse = false), parse = true))
	@TText(text="#{tedros.profileText}", wrappingWidth=480, textAlignment=TextAlignment.LEFT, 
		textStyle=TTextStyle.LARGE)
	private SimpleStringProperty profileText;
	
	@TLabel(text = "#{tedros.profile}")
	@TComboBoxField(firstItemText="#{tedros.select}",
		node=@TNode(disable=true, parse=true))
	private SimpleObjectProperty<TProfileMV> profile;
	
	//
	
	@TText(text="#{tedros.config.header}", 
	textAlignment=TextAlignment.LEFT, 
	textStyle = TTextStyle.LARGE)
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-fieldbox-title", 
	effect=@TEffect(dropShadow=@TDropShadow, parse = false), parse = true))
	private SimpleStringProperty header;
	
	@TLabel(text="#{tedros.serverip}")
	@TTextField(control=@TControl(tooltip="#{tedros.press.enter.tooltip}", parse = true),
	textInputControl=@TTextInputControl(promptText="#{tedros.serverip.text}", parse = true))
	private SimpleStringProperty serverIp;
	
	@TLabel(text="#{tedros.serverUrl}")
	@TTextField(control=@TControl(tooltip="#{tedros.press.enter.tooltip}", parse = true),
	textInputControl=@TTextInputControl(promptText="#{tedros.serverurl.text}", parse = true))
	private SimpleStringProperty url;
	
	
	@TLabel(text = "#{tedros.theme}")
	@TComboBoxField(firstItemText="#{tedros.select}")
	private SimpleStringProperty theme;
	
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
	public SimpleStringProperty toStringProperty() {
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

	public SimpleObjectProperty<TProfileMV> getProfile() {
		return profile;
	}

	public void setProfile(SimpleObjectProperty<TProfileMV> profile) {
		this.profile = profile;
	}

	public SimpleStringProperty getProfileText() {
		return profileText;
	}

	public void setProfileText(SimpleStringProperty profileText) {
		this.profileText = profileText;
	}

	/**
	 * @return the header
	 */
	public SimpleStringProperty getHeader() {
		return header;
	}

	/**
	 * @param header the header to set
	 */
	public void setHeader(SimpleStringProperty header) {
		this.header = header;
	}

	/**
	 * @return the serverIp
	 */
	public SimpleStringProperty getServerIp() {
		return serverIp;
	}

	/**
	 * @param serverIp the serverIp to set
	 */
	public void setServerIp(SimpleStringProperty serverIp) {
		this.serverIp = serverIp;
	}

	/**
	 * @return the theme
	 */
	public SimpleStringProperty getTheme() {
		return theme;
	}

	/**
	 * @param theme the theme to set
	 */
	public void setTheme(SimpleStringProperty theme) {
		this.theme = theme;
	}

	/**
	 * @return the url
	 */
	public SimpleStringProperty getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(SimpleStringProperty url) {
		this.url = url;
	}

	/**
	 * @return the name
	 */
	public SimpleStringProperty getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

}
