package com.solidarity.module.pessoa.model;

import java.util.Date;

import com.solidarity.model.Contato;
import com.solidarity.model.Documento;
import com.solidarity.model.Endereco;
import com.solidarity.model.Pessoa;
import com.solidarity.model.PessoaTermoAdesao;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.TCodeValue;
import com.tedros.fxapi.annotation.TObservableValue;
import com.tedros.fxapi.annotation.control.TContent;
import com.tedros.fxapi.annotation.control.TDatePickerField;
import com.tedros.fxapi.annotation.control.TDetailListField;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewCollectionType;
import com.tedros.fxapi.annotation.control.TPasswordField;
import com.tedros.fxapi.annotation.control.TRadioButtonField;
import com.tedros.fxapi.annotation.control.TShowField;
import com.tedros.fxapi.annotation.control.TShowField.TField;
import com.tedros.fxapi.annotation.control.TTab;
import com.tedros.fxapi.annotation.control.TTabPane;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.control.TValidator;
import com.tedros.fxapi.annotation.control.TVerticalRadioGroup;
import com.tedros.fxapi.annotation.form.TDetailForm;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.TFieldSet;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.property.TReadOnlyBooleanProperty;
import com.tedros.fxapi.annotation.reader.TDetailReaderHtml;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.reader.TTextReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.scene.layout.TRegion;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.annotation.view.TOption;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.control.TText.TTextStyle;
import com.tedros.fxapi.domain.THtmlConstant;
import com.tedros.fxapi.domain.TLabelPosition;
import com.tedros.fxapi.domain.TStyleParameter;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.util.TDateUtil;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
@TForm(name = "#{form.pessoa}", showBreadcrumBar=true)
@TEjbService(serviceName = "IPessoaControllerRemote", model=Pessoa.class)
@TListViewPresenter(listViewMinWidth=350, listViewMaxWidth=350,
	paginator=@TPaginator(entityClass = Pessoa.class, serviceName = "IPessoaControllerRemote",
			show=true, showSearchField=true, searchFieldName="nome",
			orderBy = {	@TOption(text = "#{label.codigo}", value = "id"), 
						@TOption(text = "#{label.name}", value = "nome")}),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle="#{view.pessoa}"), 
							behavior=@TBehavior(saveAllModels=false)))
@TSecurity(	id="SOLIDARITY_CADPESS_FORM", 
	appName = "#{app.name}", moduleName = "#{module.administrativo}", viewName = "#{view.pessoa}",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class PessoaModelView extends TEntityModelView<Pessoa>{
	
	private SimpleLongProperty id;
	
	private SimpleStringProperty displayText;
	
	@TReaderHtml(codeValues={@TCodeValue(code = "ATIVADO", value = "#{label.ativado}"), 
			@TCodeValue(code = "DESATIVADO", value = "#{label.desativado}")
	})
	@TLabel(text="Status", position=TLabelPosition.LEFT)
	@THorizontalRadioGroup(alignment=Pos.TOP_LEFT, spacing=4,
	radioButtons = {@TRadioButtonField(text="#{label.ativado}", userData="ATIVADO"), 
					@TRadioButtonField(text="#{label.desativado}", userData="DESATIVADO")
	})
	private SimpleStringProperty status;
	
	/**
	 * A descripton for the title and the field box
	 * */
	@TTextReaderHtml(text="#{form.person.title}", 
					htmlTemplateForControlValue="<h2 id='"+THtmlConstant.ID+"' name='"+THtmlConstant.NAME+"' style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
					cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
					cssForHtmlBox="", cssForContentValue="color:"+TStyleParameter.PANEL_TEXT_COLOR+";")
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", parse = true))
	@TText(text="#{form.person.title}", textAlignment=TextAlignment.LEFT, 
			textStyle = TTextStyle.LARGE)
	private SimpleStringProperty textoCadastro;
	
	/**
	 * A text input description for the person name and a horizontal box with name, last name and nick name
	 * */
	@TReaderHtml
	@TLabel(text="#{label.name}")
	@TTextField(maxLength=60, required = true, 
			textInputControl=@TTextInputControl(promptText="#{label.name}", parse = true), 
			node=@TNode(requestFocus=true, parse = true),
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
	@TTextAreaField(control=@TControl(prefWidth=250, prefHeight=130, parse = true), wrapText=true, maxLength=400, prefRowCount=4)
	@THBox(	pane=@TPane(children={"observacao","tipoVoluntario","statusVoluntario"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="observacao", priority=Priority.ALWAYS), 
   				   		@TPriority(field="tipoVoluntario", priority=Priority.ALWAYS),
   				   		@TPriority(field="statusVoluntario", priority=Priority.ALWAYS) }))
	private SimpleStringProperty observacao;
	
	@TLabel(text="#{label.tipo}")
	@TReaderHtml(codeValues={@TCodeValue(code = "1", value = "#{label.operacional}"), 
			@TCodeValue(code = "2", value = "#{label.estrategico}"), 
			@TCodeValue(code = "3", value = "#{label.estrategico.email}"),
			@TCodeValue(code = "4", value = "#{label.doador.filant}"),
			@TCodeValue(code = "5", value = "#{label.cad.site}"),
			@TCodeValue(code = "6", value = "#{label.outro}")
			})
	@TVerticalRadioGroup(alignment=Pos.TOP_LEFT, spacing=4,
	radioButtons = {@TRadioButtonField(text="#{label.operacional}", userData="1"), 
					@TRadioButtonField(text="#{label.estrategico}", userData="2"),
					@TRadioButtonField(text="#{label.estrategico.email}", userData="3"),
					@TRadioButtonField(text="#{label.doador.filant}", userData="4"),
					@TRadioButtonField(text="#{label.cad.site}", userData="5"),
					@TRadioButtonField(text="#{label.outro}", userData="6")
	})
	private SimpleStringProperty tipoVoluntario;
	
	@TLabel(text="#{label.situacao}")
	@TReaderHtml(codeValues={@TCodeValue(code = "1", value = "#{label.nao.contactado}"), 
			@TCodeValue(code = "2", value = "#{label.contactado}"),
			@TCodeValue(code = "3", value = "#{label.inativo}"),
			@TCodeValue(code = "4", value = "#{label.ativo}"),
			@TCodeValue(code = "5", value = "#{label.desligado}")
	})
	@TVerticalRadioGroup(alignment=Pos.TOP_LEFT, spacing=4,
	radioButtons = {@TRadioButtonField(text="#{label.nao.contactado}", userData="1"), 
					@TRadioButtonField(text="#{label.contactado}", userData="2"),
					@TRadioButtonField(text="#{label.inativo}", userData="3"),
					@TRadioButtonField(text="#{label.ativo}", userData="4"),
					@TRadioButtonField(text="#{label.desligado}", userData="5")
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
	
	/**
	 * A radio group description for the person sex
	 * */
	@TLabel(text="#{label.sex}")
	@TReaderHtml(codeValues={@TCodeValue(code = "F", value = "#{label.female}"), 
			@TCodeValue(code = "M", value = "#{label.male}")})
	@THorizontalRadioGroup(required=false, spacing=4, 
			radioButtons={	@TRadioButtonField(text = "#{label.female}", userData = "F"), 
							@TRadioButtonField(text = "#{label.male}", userData = "M")})
	@THBox(pane=@TPane(	children={"sexo", "estadoCivil", "insertDate","lastUpdate"}), spacing=10, fillHeight=true, 
		hgrow=@THGrow(priority={@TPriority(field="sexo", priority=Priority.ALWAYS),
								@TPriority(field="estadoCivil", priority=Priority.ALWAYS),
								@TPriority(field="insertDate", priority=Priority.ALWAYS), 
								@TPriority(field="lastUpdate", priority=Priority.ALWAYS)}))
	private SimpleStringProperty sexo;
	
	@TLabel(text="#{label.estado.civil}")
	@TReaderHtml(codeValues={@TCodeValue(code = "Solteiro", value = "#{label.solteiro}"), 
			@TCodeValue(code = "Casado", value = "#{label.casado}"),
			@TCodeValue(code = "Divorciado", value = "#{label.divorciado}"),
			@TCodeValue(code = "Viúvo(a)", value = "#{label.viuvo}")
	})
	@THorizontalRadioGroup(alignment=Pos.TOP_LEFT, spacing=4,
	radioButtons = {@TRadioButtonField(text="#{label.solteiro}", userData="Solteiro"), 
					@TRadioButtonField(text="#{label.casado}", userData="Casado"),
					@TRadioButtonField(text="#{label.divorciado}", userData="Divorciado"),
					@TRadioButtonField(text="#{label.viuvo}", userData="Viúvo(a)")
	})
	private SimpleStringProperty estadoCivil;
	
	
	@TReaderHtml
	@TLabel(text="#{label.cadastrado.em}")
	@TShowField(fields= {@TField(pattern=TDateUtil.DDMMYYYY_HHMM)})
	private SimpleObjectProperty<Date> insertDate;
	
	@TReaderHtml
	@TLabel(text="#{label.alterado.em}")
	@TShowField(fields= {@TField(pattern=TDateUtil.DDMMYYYY_HHMM)})
	private SimpleObjectProperty<Date> lastUpdate;
	
	@TReaderHtml
	@TLabel(text="#{label.email.login}")
	@TTextField(maxLength=80)
	@TFieldSet(fields = { "loginName", "password" }, region=@TRegion(maxWidth=400, parse = true),
		legend = "#{legend.credenciais.painel}")
	@TValidator(validatorClass = CredenciaisPainelValidator.class, associatedFieldsName={"password"})
	private SimpleStringProperty loginName;
	
	@TLabel(text="Password")
	@TPasswordField(node=@TNode(focusedProperty=@TReadOnlyBooleanProperty(
			observableValue=@TObservableValue(addListener=TEncriptPasswordChangeListener.class), parse = true), 
		parse = true))
	private SimpleStringProperty password;
	
	/**
	 * Build a tab pane with detail views to edit the collections of person documents, contacts and address.
	 * */
	@TDetailReaderHtml(	label=@TLabel(text="#{label.document}"), 
						entityClass=Documento.class, 
						modelViewClass=DocumentoModelView.class)
	@TTabPane(tabs = {
				@TTab(	text = "#{label.documents}", closable=false,
						content = @TContent(detailForm = @TDetailForm(fields= {"documentos"}))),
				@TTab(	text = "#{label.contacts}", closable=false, 
				  		content = @TContent(detailForm = @TDetailForm(fields= {"contatos"}))),
				@TTab(	text = "#{label.address}", closable=false, 
						content = @TContent(detailForm = @TDetailForm(fields= {"enderecos"}))),
				@TTab(	text = "#{label.termos.adesao}", closable=false, 
				content = @TContent(detailForm = @TDetailForm(fields= {"termosAdesao"})))})
	@TDetailListField(entityModelViewClass = DocumentoModelView.class, entityClass = Documento.class)
	@TModelViewCollectionType(modelClass=Documento.class, modelViewClass=DocumentoModelView.class)
	private ITObservableList<DocumentoModelView> documentos;
	
	@TDetailReaderHtml(label=@TLabel(text="#{label.contacts}"), entityClass=Contato.class, modelViewClass=ContatoModelView.class)
	@TDetailListField(entityModelViewClass = ContatoModelView.class, entityClass = Contato.class)
	@TModelViewCollectionType(modelClass=Contato.class, modelViewClass=ContatoModelView.class)
	private ITObservableList<ContatoModelView> contatos;
	
	@TDetailReaderHtml(label=@TLabel(text="#{label.address}"), entityClass=Endereco.class, modelViewClass=EnderecoModelView.class)
	@TDetailListField(entityModelViewClass = EnderecoModelView.class, entityClass = Endereco.class)
	@TModelViewCollectionType(modelClass=Endereco.class, modelViewClass=EnderecoModelView.class)
	private ITObservableList<EnderecoModelView> enderecos;
	
	@TDetailReaderHtml(label=@TLabel(text="#{label.termos.adesao}"), entityClass=PessoaTermoAdesao.class, modelViewClass=PessoaTermoAdesaoModelView.class)
	@TDetailListField(entityModelViewClass = PessoaTermoAdesaoModelView.class, entityClass = PessoaTermoAdesao.class)
	@TModelViewCollectionType(modelClass=PessoaTermoAdesao.class, modelViewClass=PessoaTermoAdesaoModelView.class)
	private ITObservableList<PessoaTermoAdesaoModelView> termosAdesao;
	
	private SimpleStringProperty lastPassword;
	
	public PessoaModelView(Pessoa entidade) {
		super(entidade);
		buildListener();
		loadDisplayText(entidade);
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
		buildListener();
		loadDisplayText(model);
		copyPassword();
	}
	
	/**
	 * @param model
	 */
	private void loadDisplayText(Pessoa model) {
		if(!model.isNew()){
			String str = (nome.getValue()!=null ? nome.getValue() : "") 
					+ (tipoVoluntario.getValue()!=null ? " ("+PessoaFieldValueUtil.getDescricaoTipo(tipoVoluntario.getValue())+")" : "");
			displayText.setValue(str);
		}
	}

	

	private void buildListener() {
		
		ChangeListener<String> nomeListener = super.getListenerRepository().get("displayText1");
		if(nomeListener==null){
			nomeListener = new ChangeListener<String>(){
				@SuppressWarnings("rawtypes")
				@Override
				public void changed(ObservableValue arg0, String arg1, String arg2) {
					String str = (arg2!=null ? arg2 : "") 
							+ (tipoVoluntario.getValue()!=null ? " ("+PessoaFieldValueUtil.getDescricaoTipo(tipoVoluntario.getValue())+")" : "");
					displayText.setValue(str);
				}
				
			};
			super.addListener("displayText1", nomeListener);
		}else
			nome.removeListener(nomeListener);
		
		nome.addListener(nomeListener);
		
		ChangeListener<String> tipoListener = super.getListenerRepository().get("displayText2");
		if(tipoListener==null){
			tipoListener = new ChangeListener<String>(){
				@SuppressWarnings("rawtypes")
				@Override
				public void changed(ObservableValue arg0, String arg1, String arg2) {
					String str = (nome.getValue()!=null ? nome.getValue() : "") 
							+ (arg2!=null ? " ("+PessoaFieldValueUtil.getDescricaoTipo(arg2)+")" : "");
					displayText.setValue(str);
				}
				
			};
			super.addListener("displayText2", tipoListener);
		}else
			tipoVoluntario.removeListener(tipoListener);
		
		tipoVoluntario.addListener(tipoListener);
		
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
		return displayText;
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

	/**
	 * @return the displayText
	 */
	public SimpleStringProperty getDisplayText() {
		return displayText;
	}

	/**
	 * @param displayText the displayText to set
	 */
	public void setDisplayText(SimpleStringProperty displayText) {
		this.displayText = displayText;
	}

	/**
	 * @return the estadoCivil
	 */
	public SimpleStringProperty getEstadoCivil() {
		return estadoCivil;
	}

	/**
	 * @param estadoCivil the estadoCivil to set
	 */
	public void setEstadoCivil(SimpleStringProperty estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	/**
	 * @return the termosAdesao
	 */
	public ITObservableList<PessoaTermoAdesaoModelView> getTermosAdesao() {
		return termosAdesao;
	}

	/**
	 * @param termosAdesao the termosAdesao to set
	 */
	public void setTermosAdesao(ITObservableList<PessoaTermoAdesaoModelView> termosAdesao) {
		this.termosAdesao = termosAdesao;
	}


	

}
