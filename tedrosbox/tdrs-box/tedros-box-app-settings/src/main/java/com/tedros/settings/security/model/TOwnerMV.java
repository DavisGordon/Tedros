/**
 * 
 */
package com.tedros.settings.security.model;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.core.domain.DomainApp;
import com.tedros.core.ejb.controller.TOwnerController;
import com.tedros.core.owner.model.TOwner;
import com.tedros.ejb.base.entity.ITFileEntity;
import com.tedros.fxapi.annotation.control.TContent;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TMaskField;
import com.tedros.fxapi.annotation.control.TSelectImageField;
import com.tedros.fxapi.annotation.control.TTab;
import com.tedros.fxapi.annotation.control.TTabPane;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.form.TDetailForm;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.control.TText.TTextStyle;
import com.tedros.fxapi.domain.TEnvironment;
import com.tedros.fxapi.domain.THtmlConstant;
import com.tedros.fxapi.domain.TStyleParameter;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.entity.behavior.TSaveViewBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TSaveViewDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.settings.security.action.TOwnerNewAction;
import com.tedros.settings.start.TConstant;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

/**
 * @author Davis Gordon
 *
 */
@TFormReaderHtml
@TForm(name="#{owner.form}", showBreadcrumBar=true, editCssId="")
@TEjbService(serviceName = TOwnerController.JNDI_NAME, model=TOwner.class)
@TPresenter(type=TDynaPresenter.class, 
			decorator=@TDecorator(type = TSaveViewDecorator.class, viewTitle="#{owner.view}"),
			behavior=@TBehavior(type=TSaveViewBehavior.class, action=TOwnerNewAction.class))
@TSecurity(	id=DomainApp.OWNER_FORM_ID, 
			appName="#{settings.app.name}", 
			moduleName="#{label.owner}", 
			viewName="#{owner.view}",
			allowedAccesses={	TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
			   					TAuthorizationType.NEW, TAuthorizationType.SAVE, TAuthorizationType.DELETE})
public class TOwnerMV extends TEntityModelView<TOwner> {

	private SimpleLongProperty id;
	
	@TTabPane(tabs = { 
			@TTab(closable=false, scroll=false, content = @TContent(detailForm=
				@TDetailForm(fields={"header", "name", "tel", "token"})), 
				text = "#{label.main}"), 
			@TTab(closable=false, scroll=false, content = @TContent(detailForm=
				@TDetailForm(fields={"logo"})), 
				text = "#{label.logo}")
	})
	@TReaderHtml(htmlTemplateForControlValue="<h2 style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
				cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
				cssForHtmlBox="", cssForContentValue="")
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-pane", parse = true))
	@TText(text="#{header.owner}", textAlignment=TextAlignment.LEFT, 
			textStyle = TTextStyle.LARGE)
	private SimpleStringProperty header;
	
	@TReaderHtml
	@TLabel(text="#{label.name}:")
	@TTextField(maxLength=60, required=true)
	@THBox(	pane=@TPane(children={"name","organization"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="name", priority=Priority.ALWAYS), 
		   		@TPriority(field="organization", priority=Priority.ALWAYS)}))
	private SimpleStringProperty name;
	
	@TReaderHtml
	@TLabel(text="#{label.organization}:")
	@TTextField(maxLength=60)
	private SimpleStringProperty organization;
	
	@TReaderHtml
	@TLabel(text="#{label.tel}:")
	@TMaskField(maxLength=20, mask = "(99) 999999999")
	@THBox(	pane=@TPane(children={"tel","email"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="tel", priority=Priority.ALWAYS), 
		   		@TPriority(field="email", priority=Priority.ALWAYS)}))
	private SimpleStringProperty tel;
	
	@TReaderHtml
	@TLabel(text="#{label.email}:")
	@TTextField(maxLength=60)
	private SimpleStringProperty email;
	
	@TReaderHtml
	@TLabel(text="#{label.token}:")
	@TTextField(maxLength=120)
	private SimpleStringProperty token;
	
	@TFieldBox(node=@TNode(id="img", parse = true))
	@TSelectImageField(source=TEnvironment.LOCAL, target=TEnvironment.REMOTE, fitHeight=-1,
	remoteOwner=DomainApp.MNEMONIC)
	private SimpleObjectProperty<ITFileEntity> logo;
	
	public TOwnerMV() {
		super(new TOwner());
	}
	
	public TOwnerMV(TOwner entity) {
		super(entity);
	}

	@Override
	public void reload(TOwner model) {
		super.reload(model);
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
	 * @return the organization
	 */
	public SimpleStringProperty getOrganization() {
		return organization;
	}

	/**
	 * @param organization the organization to set
	 */
	public void setOrganization(SimpleStringProperty organization) {
		this.organization = organization;
	}

	/**
	 * @return the tel
	 */
	public SimpleStringProperty getTel() {
		return tel;
	}

	/**
	 * @param tel the tel to set
	 */
	public void setTel(SimpleStringProperty tel) {
		this.tel = tel;
	}

	/**
	 * @return the email
	 */
	public SimpleStringProperty getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(SimpleStringProperty email) {
		this.email = email;
	}

	/**
	 * @return the token
	 */
	public SimpleStringProperty getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(SimpleStringProperty token) {
		this.token = token;
	}

	/**
	 * @return the logo
	 */
	public SimpleObjectProperty<ITFileEntity> getLogo() {
		return logo;
	}

	/**
	 * @param logo the logo to set
	 */
	public void setLogo(SimpleObjectProperty<ITFileEntity> logo) {
		this.logo = logo;
	}
}
