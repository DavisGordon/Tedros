package com.tedros.settings.security.model;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.core.security.model.TAuthorization;
import com.tedros.core.security.model.TProfile;
import com.tedros.fxapi.annotation.control.TCallbackFactory;
import com.tedros.fxapi.annotation.control.TCellFactory;
import com.tedros.fxapi.annotation.control.TCellValueFactory;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewCollectionType;
import com.tedros.fxapi.annotation.control.TMultipleSelectionModal;
import com.tedros.fxapi.annotation.control.TTableColumn;
import com.tedros.fxapi.annotation.control.TTableView;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.TAccordion;
import com.tedros.fxapi.annotation.layout.TTitledPane;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEntityProcess;
import com.tedros.fxapi.annotation.reader.TDetailReader;
import com.tedros.fxapi.annotation.reader.TReader;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.text.TFont;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.entity.behavior.TMasterCrudViewBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TMasterCrudViewDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.settings.security.action.TProfileCompleteTableViewAction;
import com.tedros.settings.security.callback.CheckBoxEnableCallBack;
import com.tedros.settings.security.process.TProfileProcess;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.text.TextAlignment;

/**
 * @author Davis Gordon
 */
@TForm(name="#{security.profile.form.name}")
@TPresenter(type=TDynaPresenter.class,
			modelClass=TProfile.class,
			decorator=@TDecorator(	type=TMasterCrudViewDecorator.class, 
									viewTitle="#{security.profile.view.title}", 
									listTitle="#{security.profile.list.title}"))
@TSecurity(	id="T_CUSTOM_SECURITY_PROFILE", 
			appName="#{settings.app.name}", 
			moduleName="#{label.profile}", 
			viewName="#{security.profile.view.title}",
			allowedAccesses={	TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
			   					TAuthorizationType.NEW, TAuthorizationType.SAVE, TAuthorizationType.DELETE})
@TEntityProcess(entity=TProfile.class, process=TProfileProcess.class)
public final class TProfileModelView extends TEntityModelView<TProfile> {
	
	private SimpleLongProperty id;
	
	@TAccordion(expandedPane="main", node=@TNode(id="profileAcc",parse = true),
			panes={
					@TTitledPane(text="Principal", node=@TNode(id="main",parse = true), expanded=true,
							fields={"textoCadastro", "name", "description"}),
					@TTitledPane(text="Detalhe", node=@TNode(id="detail",parse = true),
						fields={"autorizations"})})
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", parse = true))
	@TText(text="#{label.profile.header}", font=@TFont(size=22), textAlignment=TextAlignment.LEFT, 
		node=@TNode(id="t-form-title-text", parse = true))
	private SimpleStringProperty textoCadastro;
	
	@TReader
	@TLabel(text="#{label.name}")
	@TTextField(maxLength=100, required=true,
				textInputControl=@TTextInputControl(promptText="#{prompt.profile.name}", parse=true), 
				control=@TControl(minWidth=100, parse=true))
	private SimpleStringProperty name;
	
	@TReader
	@TLabel(text="#{label.description}")
	@TTextAreaField(maxLength=600, 
					textInputControl=@TTextInputControl(promptText="#{prompt.profile.description}", parse=true),
					prefRowCount=3)
	private SimpleStringProperty description;
	
	@TDetailReader(label=@TLabel(text="Authorization"))
	@TMultipleSelectionModal(modelClass = TAuthorization.class, modelViewClass = TAuthorizationTableView.class,
	width=600, height=350)
	@TModelViewCollectionType(modelClass=TAuthorization.class, modelViewClass=TAuthorizationTableView.class)
	private ITObservableList<TAuthorizationTableView> autorizations;

	public TProfileModelView(TProfile entity) {
		super(entity);
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

	public ITObservableList<TAuthorizationTableView> getAutorizations() {
		return autorizations;
	}

	public void setAutorizations(
			ITObservableList<TAuthorizationTableView> autorizations) {
		this.autorizations = autorizations;
	}

}
