package org.tedros.tools.module.user.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.core.controller.TProfileController;
import org.tedros.core.domain.DomainApp;
import org.tedros.core.security.model.TAuthorization;
import org.tedros.core.security.model.TProfile;
import org.tedros.fx.annotation.control.TContent;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TModelViewType;
import org.tedros.fx.annotation.control.TMultipleSelectionModal;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.control.TTextInputControl;
import org.tedros.fx.annotation.form.TDetailForm;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.reader.TDetailReader;
import org.tedros.fx.annotation.reader.TReader;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.annotation.text.TText;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.entity.decorator.TMasterCrudViewDecorator;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.module.user.table.TAuthorizationTV;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.text.TextAlignment;

/**
 * @author Davis Gordon
 */
@TForm(name=ToolsKey.SECURITY_PROFILE_FORM_NAME, showBreadcrumBar=true, editCssId="")
@TEjbService(serviceName = TProfileController.JNDI_NAME, model=TProfile.class)
@TPresenter(type=TDynaPresenter.class,
			modelClass=TProfile.class,
			decorator=@TDecorator(	type=TMasterCrudViewDecorator.class, 
									viewTitle=ToolsKey.VIEW_PROFILE, 
									listTitle=ToolsKey.SECURITY_PROFILE_LIST_TITLE))
@TSecurity(	id=DomainApp.PROFILE_FORM_ID, 
			appName=ToolsKey.APP_TOOLS, 
			moduleName=ToolsKey.MODULE_USER, 
			viewName=ToolsKey.VIEW_PROFILE,
			allowedAccesses={	TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
			   					TAuthorizationType.NEW, TAuthorizationType.SAVE, TAuthorizationType.DELETE})
public final class TProfileMV extends TEntityModelView<TProfile> {
	
	private SimpleLongProperty id;
	
	@TTabPane(tabs = { 
			@TTab(closable=false, scroll=false, content = @TContent(detailForm=
				@TDetailForm(fields={"textoCadastro", "name", "description"})), 
				text = ToolsKey.MAIN), 
			@TTab(closable=false, content = @TContent(detailForm=
				@TDetailForm(fields={"autorizations"})), 
				text = ToolsKey.DETAIL)
	})
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", parse = true))
	@TText(text=ToolsKey.PROFILE_HEADER, textAlignment=TextAlignment.LEFT, 
			textStyle = TTextStyle.LARGE)
	private SimpleStringProperty textoCadastro;
	
	@TReader
	@TLabel(text=ToolsKey.NAME)
	@TTextField(maxLength=100, required=true,
				textInputControl=@TTextInputControl(promptText=ToolsKey.PROMPT_PROFILE_NAME, parse=true), 
				control=@TControl(minWidth=100, parse=true))
	private SimpleStringProperty name;
	
	@TReader
	@TLabel(text=ToolsKey.DESCRIPTION)
	@TTextAreaField(maxLength=600, 
					textInputControl=@TTextInputControl(promptText=ToolsKey.PROMPT_PROFILE_DESCRIPTION, parse=true),
					prefRowCount=3)
	private SimpleStringProperty description;
	
	@TDetailReader(label=@TLabel(text="Authorization"))
	@TMultipleSelectionModal(width=600, height=350,
	modelClass = TAuthorization.class, modelViewClass = TAuthorizationTV.class)
	@TModelViewType(modelClass=TAuthorization.class, modelViewClass=TAuthorizationTV.class)
	private ITObservableList<TAuthorizationTV> autorizations;

	public TProfileMV(TProfile entity) {
		super(entity);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof TProfileMV))
			return false;
		return EqualsBuilder.reflectionEquals(this.getModel(), obj != null ? ((TProfileMV)obj).getModel() : obj, false);
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

	public SimpleStringProperty getDescription() {
		return description;
	}

	public void setDescription(SimpleStringProperty description) {
		this.description = description;
	}

	public SimpleStringProperty getTextoCadastro() {
		return textoCadastro;
	}

	public void setTextoCadastro(SimpleStringProperty textoCadastro) {
		this.textoCadastro = textoCadastro;
	}

	public ITObservableList<TAuthorizationTV> getAutorizations() {
		return autorizations;
	}

	public void setAutorizations(
			ITObservableList<TAuthorizationTV> autorizations) {
		this.autorizations = autorizations;
	}

}
