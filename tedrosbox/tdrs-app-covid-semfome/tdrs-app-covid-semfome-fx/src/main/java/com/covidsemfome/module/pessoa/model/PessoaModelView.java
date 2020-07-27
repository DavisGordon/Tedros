package com.covidsemfome.module.pessoa.model;

import java.util.Date;

import com.covidsemfome.model.Contato;
import com.covidsemfome.model.Documento;
import com.covidsemfome.model.Endereco;
import com.covidsemfome.model.Pessoa;
import com.covidsemfome.module.pessoa.process.TPessoaProcess;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.TCodeValue;
import com.tedros.fxapi.annotation.TObservableValue;
import com.tedros.fxapi.annotation.control.TContent;
import com.tedros.fxapi.annotation.control.TDatePickerField;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TLabelPosition;
import com.tedros.fxapi.annotation.control.TModelViewCollectionType;
import com.tedros.fxapi.annotation.control.TPasswordField;
import com.tedros.fxapi.annotation.control.TRadioButtonField;
import com.tedros.fxapi.annotation.control.TTab;
import com.tedros.fxapi.annotation.control.TTabPane;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.control.TVerticalRadioGroup;
import com.tedros.fxapi.annotation.effect.TDropShadow;
import com.tedros.fxapi.annotation.effect.TEffect;
import com.tedros.fxapi.annotation.form.TDetailView;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEntityProcess;
import com.tedros.fxapi.annotation.property.TReadOnlyBooleanProperty;
import com.tedros.fxapi.annotation.reader.TDetailReaderHtml;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.reader.TTextReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.text.TFont;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.builder.DateTimeFormatBuilder;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.domain.THtmlConstant;
import com.tedros.fxapi.domain.TStyleParameter;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.entity.behavior.TMainCrudViewWithListViewBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TMainCrudViewWithListViewDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

/**
 * The person model view
 * 
 * An entity model view class wraps an entity and is responsable 
 * for descript how the crud view and the fields must be build and exposed.
 * 
 * @author Davis Gordon
 * */
@TFormReaderHtml
@TForm(name = "#{form.person.title}", showBreadcrumBar=true)
@TEntityProcess(process = TPessoaProcess.class, entity=Pessoa.class)
@TPresenter(type = TDynaPresenter.class,
			behavior = @TBehavior(type = TMainCrudViewWithListViewBehavior.class), 
			decorator = @TDecorator(type = TMainCrudViewWithListViewDecorator.class, 
									viewTitle="#{view.person.name}", listTitle="#{label.select}"))
@TSecurity(	id="COVSEMFOME_CADPESS_FORM", 
			appName = "#{app.name}", moduleName = "Gerenciar Campanha", viewName = "#{view.person.name}",
			allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
							TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})

public class PessoaModelView extends TEntityModelView<Pessoa>{
	
	/*@TReaderHtml
	@TLabel(text="Codigo do registro:", position=TLabelPosition.LEFT)
	@TLongField(maxLength=6,node=@TNode(parse = true, disable=true) )
	@THBox(	pane=@TPane(children={"id","status"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="id", priority=Priority.NEVER), 
   				   		@TPriority(field="status", priority=Priority.ALWAYS)}))*/
	private SimpleLongProperty id;
	
	@TReaderHtml(codeValues={@TCodeValue(code = "ATIVADO", value = "Ativado"), 
			@TCodeValue(code = "DESATIVADO", value = "Desativado")
	})
	@TLabel(text="Status", position=TLabelPosition.LEFT)
	@THorizontalRadioGroup(alignment=Pos.TOP_LEFT, spacing=4,
	radioButtons = {@TRadioButtonField(text="Ativado", userData="ATIVADO"), 
					@TRadioButtonField(text="Desativado", userData="DESATIVADO")
	})
	private SimpleStringProperty status;
	
	/**
	 * A descripton for the title and the field box
	 * */
	@TTextReaderHtml(text="#{form.person.title}", 
					htmlTemplateForControlValue="<h2 id='"+THtmlConstant.ID+"' name='"+THtmlConstant.NAME+"' style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
					cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
					cssForHtmlBox="", cssForContentValue="color:"+TStyleParameter.PANEL_TEXT_COLOR+";")
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", effect=@TEffect(dropShadow=@TDropShadow, parse=true), parse = true))
	@TText(	text="#{form.person.title}", font=@TFont(size=22), textAlignment=TextAlignment.LEFT, 
			node=@TNode(id="t-form-title-text", parse = true))
	private SimpleStringProperty textoCadastro;
	
	/**
	 * A text input description for the person name and a horizontal box with name, last name and nick name
	 * */
	@TReaderHtml
	@TLabel(text="#{label.name}")
	@TTextField(maxLength=60, required = true, textInputControl=@TTextInputControl(promptText="#{label.name}", parse = true), 
				control=@TControl(tooltip="#{label.name}", parse = true))
	@THBox(	pane=@TPane(children={"nome","profissao","dataNascimento"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="nome", priority=Priority.ALWAYS), 
						@TPriority(field="profissao", priority=Priority.ALWAYS), 
   				   		@TPriority(field="dataNascimento", priority=Priority.ALWAYS)}))
	private SimpleStringProperty nome;
	
	/**
	 * A text area input description for the person observation
	 * */
	@TReaderHtml
	@TLabel(text="#{label.observation}")
	@TTextAreaField(control=@TControl(prefWidth=250, prefHeight=50, parse = true), wrapText=true, maxLength=400, prefRowCount=4)
	@THBox(	pane=@TPane(children={"observacao","tipoVoluntario","statusVoluntario"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="observacao", priority=Priority.ALWAYS), 
   				   		@TPriority(field="tipoVoluntario", priority=Priority.ALWAYS),
   				   		@TPriority(field="statusVoluntario", priority=Priority.ALWAYS) }))
	private SimpleStringProperty observacao;
	
	@TReaderHtml(codeValues={@TCodeValue(code = "1", value = "Operacional"), 
			@TCodeValue(code = "2", value = "Estrategico"), 
			@TCodeValue(code = "3", value = "Estrategico (Receber emails)")
			})
	@TLabel(text="Tipo voluntário")
	@TVerticalRadioGroup(alignment=Pos.TOP_LEFT, spacing=4,
	radioButtons = {@TRadioButtonField(text="Operacional", userData="1"), 
					@TRadioButtonField(text="Estrategico", userData="2"),
					@TRadioButtonField(text="Estrategico (Receber emails)", userData="3")
	})
	private SimpleStringProperty tipoVoluntario;
	
	@TReaderHtml(codeValues={@TCodeValue(code = "1", value = "Aguardando"), 
			@TCodeValue(code = "2", value = "Contactado"),
			@TCodeValue(code = "3", value = "Voluntario"),
			@TCodeValue(code = "4", value = "Voluntario Ativo"),
			@TCodeValue(code = "5", value = "Voluntario problematico"),
			@TCodeValue(code = "6", value = "Desligado")
	})
	@TLabel(text="Situação")
	@TVerticalRadioGroup(alignment=Pos.TOP_LEFT, spacing=4,
	radioButtons = {@TRadioButtonField(text="Aguardando", userData="1"), 
					@TRadioButtonField(text="Contactado", userData="2"),
					@TRadioButtonField(text="Voluntario", userData="3"),
					@TRadioButtonField(text="Voluntario Ativo", userData="4"),
					@TRadioButtonField(text="Voluntario problematico", userData="5"),
					@TRadioButtonField(text="Desligado ", userData="6")
	})
	private SimpleStringProperty statusVoluntario;
	
	
	/**
	 * A text input description for the person occupation
	 * */
	@TReaderHtml
	@TLabel(text="#{label.occupation}")
	@TTextField(maxLength=80)
	private SimpleStringProperty profissao;
	
	/**
	 * A date input description for the person birth date and a horizontal box with birth date and sex
	 * */
	@TReaderHtml
	@TLabel(text="#{label.birthdate}")
	@TDatePickerField(required = false)
	private SimpleObjectProperty<Date> dataNascimento;
	
	@TReaderHtml
	@TLabel(text="Email (Login no painel)")
	@TTextField(maxLength=80)
	@THBox(pane=@TPane(	children={"loginName","password"}), spacing=10, fillHeight=true, 
	hgrow=@THGrow(priority={@TPriority(field="loginName", priority=Priority.NEVER),
							@TPriority(field="password", priority=Priority.NEVER)}))
	private SimpleStringProperty loginName;
	
	@TLabel(text="Password")
	@TPasswordField(required=true, 
					node=@TNode(focusedProperty=@TReadOnlyBooleanProperty(
												observableValue=@TObservableValue(addListener=TEncriptPasswordChangeListener.class), 
												parse = true), 
								parse = true))
	private SimpleStringProperty password;
	
	/**
	 * A radio group description for the person sex
	 * */
	@TReaderHtml(codeValues={@TCodeValue(code = "F", value = "#{label.female}"), @TCodeValue(code = "M", value = "#{label.male}")})
	@TLabel(text="#{label.sex}")
	@THorizontalRadioGroup(required=false, spacing=4, 
			radioButtons={	@TRadioButtonField(text = "#{label.female}", userData = "F"), 
							@TRadioButtonField(text = "#{label.male}", userData = "M")})
	@THBox(pane=@TPane(	children={"sexo","insertDate","lastUpdate"}), spacing=10, fillHeight=true, 
		hgrow=@THGrow(priority={@TPriority(field="sexo", priority=Priority.ALWAYS),
								@TPriority(field="insertDate", priority=Priority.ALWAYS), 
								@TPriority(field="lastUpdate", priority=Priority.ALWAYS)}))
	private SimpleStringProperty sexo;
	
	@TReaderHtml
	@TLabel(text="Cadastrado em")
	@TDatePickerField(required = false, node=@TNode(parse = true, disable=true), dateFormat=DateTimeFormatBuilder.class)
	private SimpleObjectProperty<Date> insertDate;
	
	@TReaderHtml
	@TLabel(text="Alterado em")
	@TDatePickerField(required = false, node=@TNode(parse = true, disable=true), dateFormat=DateTimeFormatBuilder.class)
	private SimpleObjectProperty<Date> lastUpdate;
	
	/**
	 * A descripton for the personal additional data sub title and the field box
	 * */
	/*@TTextReaderHtml(text="#{label.additionaldata}", 
			htmlTemplateForControlValue="<h2 style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
			cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
			cssForHtmlBox="", cssForContentValue="")
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", parse = true))
	@TText(	text="#{label.additionaldata}", font=@TFont(size=22), textAlignment=TextAlignment.LEFT, 
			node=@TNode(id="t-form-title-text", parse = true))
	*/private SimpleStringProperty textoDetail;
	
	/**
	 * A descripton to build a tab pane with detail views to edit the collections of person documents, contacts and address.
	 * */
	@TDetailReaderHtml(	label=@TLabel(text="#{label.document}"), 
						entityClass=Documento.class, 
						modelViewClass=DocumentoModelView.class)
	@TTabPane(tabs = {
				@TTab(	text = "#{label.documents}", closable=false,
						content = @TContent(detailView=@TDetailView(field="documentos", formTitle="#{label.document}", 
																	listTitle = "#{label.document}", propertyType=ITObservableList.class, 
																	entityClass=Documento.class, entityModelViewClass=DocumentoModelView.class))),
				@TTab(	text = "#{label.contacts}", closable=false, 
				  		content = @TContent(detailView=@TDetailView(field="contatos", formTitle="#{label.contact}", 
				  													listTitle = "#{label.contacts}", propertyType=ITObservableList.class, 
				  													entityClass=Contato.class, entityModelViewClass=ContatoModelView.class))),
				@TTab(	text = "#{label.address}", closable=false, 
						content = @TContent(detailView=@TDetailView(field="enderecos", formTitle="#{label.address}", 
																	listTitle = "Endereco", propertyType=ITObservableList.class, 
																	entityClass=Endereco.class, entityModelViewClass=EnderecoModelView.class)))})
	@TModelViewCollectionType(modelClass=Documento.class, modelViewClass=DocumentoModelView.class)
	private ITObservableList<DocumentoModelView> documentos;
	
	@TDetailReaderHtml(label=@TLabel(text="#{label.contacts}"), entityClass=Contato.class, modelViewClass=ContatoModelView.class)
	@TModelViewCollectionType(modelClass=Contato.class, modelViewClass=ContatoModelView.class)
	private ITObservableList<ContatoModelView> contatos;
	
	@TDetailReaderHtml(label=@TLabel(text="#{label.address}"), entityClass=Endereco.class, modelViewClass=EnderecoModelView.class)
	@TModelViewCollectionType(modelClass=Endereco.class, modelViewClass=EnderecoModelView.class)
	private ITObservableList<EnderecoModelView> enderecos;
	
	private SimpleStringProperty lastPassword;
	
	public PessoaModelView(Pessoa entidade) {
		super(entidade);
		copyPassword();
		
	}
	
	private void copyPassword() {
		if(password!=null && password.getValue()!=null)
			lastPassword.setValue(password.getValue());
		else
			lastPassword.setValue("");
	}
	
	@Override
	public void reload(Pessoa model) {
		super.reload(model);
		copyPassword();
	}
	
	@Override
	public void removeAllListener() {
		super.removeAllListener();
	}
	
	public PessoaModelView() {
		super(new Pessoa());
	}
	
	public  PessoaModelView getNewInstance(){
		return new PessoaModelView(new Pessoa());
	}
	
	@Override
	public String toString() {
		return (getNome()!=null)? getNome().getValue() : "";	
	}
		
	@Override
	public int hashCode() {
		return reflectionHashCode(this, null);
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj == null || !(obj instanceof PessoaModelView))
			return false;
		
		PessoaModelView p = (PessoaModelView) obj;
		
		if(getId()!=null && getId().getValue()!=null &&  p.getId()!=null && p.getId().getValue()!=null){
			if(!(getId().getValue().equals(Long.valueOf(0)) && p.getId().getValue().equals(Long.valueOf(0))))
				return getId().getValue().equals(p.getId().getValue());
		}	
		
		if(getNome()!=null && getNome().getValue()!=null &&  p.getNome()!=null && p.getNome().getValue()!=null)
			return getNome().getValue().equals(p.getNome().getValue());
		
		return false;
	}

	public SimpleLongProperty getId() {
		return id;
	}

	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	public SimpleStringProperty getTextoCadastro() {
		return textoCadastro;
	}

	public void setTextoCadastro(SimpleStringProperty textoCadastro) {
		this.textoCadastro = textoCadastro;
	}

	public SimpleStringProperty getNome() {
		return nome;
	}

	public void setNome(SimpleStringProperty nome) {
		this.nome = nome;
	}

	public SimpleStringProperty getProfissao() {
		return profissao;
	}

	public void setProfissao(SimpleStringProperty profissao) {
		this.profissao = profissao;
	}

	public SimpleObjectProperty<Date> getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(SimpleObjectProperty<Date> dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public SimpleStringProperty getSexo() {
		return sexo;
	}

	public void setSexo(SimpleStringProperty sexo) {
		this.sexo = sexo;
	}

	public SimpleStringProperty getObservacao() {
		return observacao;
	}

	public void setObservacao(SimpleStringProperty observacao) {
		this.observacao = observacao;
	}

	public ITObservableList<DocumentoModelView> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(ITObservableList<DocumentoModelView> documentos) {
		this.documentos = documentos;
	}

	public ITObservableList<ContatoModelView> getContatos() {
		return contatos;
	}

	public void setContatos(ITObservableList<ContatoModelView> contatos) {
		this.contatos = contatos;
	}

	public ITObservableList<EnderecoModelView> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(ITObservableList<EnderecoModelView> enderecos) {
		this.enderecos = enderecos;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		//System.out.println("Versao: "+getModel().getVersionNum());
		return getNome();
	}

	public SimpleStringProperty getTextoDetail() {
		return textoDetail;
	}

	public void setTextoDetail(SimpleStringProperty textoDetail) {
		this.textoDetail = textoDetail;
	}

	/**
	 * @return the tipoVoluntario
	 */
	public SimpleStringProperty getTipoVoluntario() {
		return tipoVoluntario;
	}

	/**
	 * @param tipoVoluntario the tipoVoluntario to set
	 */
	public void setTipoVoluntario(SimpleStringProperty tipoVoluntario) {
		this.tipoVoluntario = tipoVoluntario;
	}

	/**
	 * @return the statusVoluntario
	 */
	public SimpleStringProperty getStatusVoluntario() {
		return statusVoluntario;
	}

	/**
	 * @param statusVoluntario the statusVoluntario to set
	 */
	public void setStatusVoluntario(SimpleStringProperty statusVoluntario) {
		this.statusVoluntario = statusVoluntario;
	}

	/**
	 * @return the insertDate
	 */
	public SimpleObjectProperty<Date> getInsertDate() {
		return insertDate;
	}

	/**
	 * @param insertDate the insertDate to set
	 */
	public void setInsertDate(SimpleObjectProperty<Date> insertDate) {
		this.insertDate = insertDate;
	}

	/**
	 * @return the lastUpdate
	 */
	public SimpleObjectProperty<Date> getLastUpdate() {
		return lastUpdate;
	}

	/**
	 * @param lastUpdate the lastUpdate to set
	 */
	public void setLastUpdate(SimpleObjectProperty<Date> lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	/**
	 * @return the status
	 */
	public SimpleStringProperty getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(SimpleStringProperty status) {
		this.status = status;
	}

	/**
	 * @return the loginName
	 */
	public SimpleStringProperty getLoginName() {
		return loginName;
	}

	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(SimpleStringProperty loginName) {
		this.loginName = loginName;
	}

	/**
	 * @return the password
	 */
	public SimpleStringProperty getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(SimpleStringProperty password) {
		this.password = password;
	}

	/**
	 * @return the lastPassword
	 */
	public SimpleStringProperty getLastPassword() {
		return lastPassword;
	}

	/**
	 * @param lastPassword the lastPassword to set
	 */
	public void setLastPassword(SimpleStringProperty lastPassword) {
		this.lastPassword = lastPassword;
	}


	

}
