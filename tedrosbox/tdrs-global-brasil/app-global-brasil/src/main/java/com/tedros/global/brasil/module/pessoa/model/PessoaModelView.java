package com.tedros.global.brasil.module.pessoa.model;

import java.util.Date;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.TCodeValue;
import com.tedros.fxapi.annotation.control.TContent;
import com.tedros.fxapi.annotation.control.TDatePickerField;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewCollectionType;
import com.tedros.fxapi.annotation.control.TRadioButtonField;
import com.tedros.fxapi.annotation.control.TTab;
import com.tedros.fxapi.annotation.control.TTabPane;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
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
import com.tedros.fxapi.annotation.reader.TDetailReaderHtml;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.reader.TTextReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.text.TFont;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.domain.THtmlConstant;
import com.tedros.fxapi.domain.TStyleParameter;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.entity.behavior.TMainCrudViewWithListViewBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TMainCrudViewWithListViewDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.global.brasil.model.Contato;
import com.tedros.global.brasil.model.Documento;
import com.tedros.global.brasil.model.Endereco;
import com.tedros.global.brasil.model.Pessoa;
import com.tedros.global.brasil.module.pessoa.process.TPessoaProcess;

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
@TSecurity(	id="T_APP_GLOBAL_BRASIL_CAP_PESSOA_PESSOA_FORM", 
			appName = "#{app.name}", moduleName = "#{label.person}", viewName = "#{view.person.name}",
			allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
							TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})

public class PessoaModelView extends TEntityModelView<Pessoa>{
	
	private SimpleLongProperty id;
	
	/**
	 * A descripton for the title and the field box
	 * */
	@TTextReaderHtml(text="#{form.person.title}", 
					htmlTemplateForControlValue="<h2 id='"+THtmlConstant.ID+"' name='"+THtmlConstant.NAME+"' style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
					cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
					cssForHtmlBox="", cssForContentValue="")
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
	@THBox(	pane=@TPane(children={"nome","sobreNome","apelido"}), spacing=10, fillHeight=true, 
	   		hgrow=@THGrow(priority={@TPriority(field="nome", priority=Priority.ALWAYS), 
			   				   		@TPriority(field="sobreNome", priority=Priority.ALWAYS),
			   				   		@TPriority(field="apelido", priority=Priority.ALWAYS) }))
	private SimpleStringProperty nome;
	
	/**
	 * A text input description for the person last name
	 * */
	@TReaderHtml
	@TLabel(text="#{label.lastname}")
	@TTextField(maxLength=60)
	private SimpleStringProperty sobreNome;
	
	/**
	 * A text input description for the person nick name
	 * */
	@TReaderHtml
	@TLabel(text="#{label.nickname}")
	@TTextField(maxLength=60)
	private SimpleStringProperty apelido;
	
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
	@THBox(pane=@TPane(	children={"dataNascimento","sexo"}), spacing=10, fillHeight=true, 
	   					hgrow=@THGrow(priority={@TPriority(field="dataNascimento", priority=Priority.ALWAYS), 
	   											@TPriority(field="sexo", priority=Priority.ALWAYS)}))
	private SimpleObjectProperty<Date> dataNascimento;
	
	/**
	 * A radio group description for the person sex
	 * */
	@TReaderHtml(codeValues={@TCodeValue(code = "F", value = "#{label.female}"), @TCodeValue(code = "M", value = "#{label.male}")})
	@TLabel(text="#{label.sex}")
	@THorizontalRadioGroup(required=true, spacing=4, 
			radioButtons={	@TRadioButtonField(text = "#{label.female}", userData = "F"), 
							@TRadioButtonField(text = "#{label.male}", userData = "M")})
	private SimpleStringProperty sexo;
	
	/**
	 * A text area input description for the person observation
	 * */
	@TReaderHtml
	@TLabel(text="#{label.observation}")
	@TTextAreaField(control=@TControl(prefWidth=250, prefHeight=50, parse = true), maxLength=400, prefRowCount=4)
	private SimpleStringProperty observacao;
	
	/**
	 * A descripton for the personal additional data sub title and the field box
	 * */
	@TTextReaderHtml(text="#{label.additionaldata}", 
			htmlTemplateForControlValue="<h2 style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
			cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
			cssForHtmlBox="", cssForContentValue="")
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", parse = true))
	@TText(	text="#{label.additionaldata}", font=@TFont(size=22), textAlignment=TextAlignment.LEFT, 
			node=@TNode(id="t-form-title-text", parse = true))
	private SimpleStringProperty textoDetail;
	
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
	@TModelViewCollectionType(entityClass=Documento.class, modelViewClass=DocumentoModelView.class)
	private ITObservableList<DocumentoModelView> documentos;
	
	@TDetailReaderHtml(label=@TLabel(text="#{label.contacts}"), entityClass=Contato.class, modelViewClass=ContatoModelView.class)
	@TModelViewCollectionType(entityClass=Contato.class, modelViewClass=ContatoModelView.class)
	private ITObservableList<ContatoModelView> contatos;
	
	@TDetailReaderHtml(label=@TLabel(text="#{label.address}"), entityClass=Endereco.class, modelViewClass=EnderecoModelView.class)
	@TModelViewCollectionType(entityClass=Endereco.class, modelViewClass=EnderecoModelView.class)
	private ITObservableList<EnderecoModelView> enderecos;
	
	/* Used to test if the listener was removed after been used, need to remove comment in the line 365 at TListenerHelp  
	 * 
	@TIgnoreField
	private Timeline tl;
	
	@TIgnoreField
	private EventHandler<ActionEvent> eh;
	*/
	public PessoaModelView(Pessoa entidade) {
		super(entidade);
		/* Used to test if the listener was removed after been used, need to remove comment in the line 365 at TListenerHelp 
		eh = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	nome.setValue(UUID.randomUUID().toString());
            	//textoCadastro.setValue(RandomStringUtils.randomAlphanumeric(10));
            	documentos.add(new DocumentoModelView(new Documento(RandomStringUtils.randomAlphanumeric(6), "1")));		
            }
        };
		
		tl = TimelineBuilder.create().keyFrames(
	            new KeyFrame(Duration.millis(10000), eh))
	        .cycleCount(Animation.INDEFINITE)
	        .build();
		
		tl.play();*/
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

	public SimpleStringProperty getSobreNome() {
		return sobreNome;
	}

	public void setSobreNome(SimpleStringProperty sobreNome) {
		this.sobreNome = sobreNome;
	}

	public SimpleStringProperty getApelido() {
		return apelido;
	}

	public void setApelido(SimpleStringProperty apelido) {
		this.apelido = apelido;
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
		return getNome();
	}

	public SimpleStringProperty getTextoDetail() {
		return textoDetail;
	}

	public void setTextoDetail(SimpleStringProperty textoDetail) {
		this.textoDetail = textoDetail;
	}


	

}
