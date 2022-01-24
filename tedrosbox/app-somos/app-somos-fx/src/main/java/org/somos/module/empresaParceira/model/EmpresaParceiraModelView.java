package org.somos.module.empresaParceira.model;

import org.somos.parceiro.model.EmpresaParceira;

import com.tedros.common.model.TFileEntity;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.control.TContent;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.TFileField;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TMaskField;
import com.tedros.fxapi.annotation.control.TTab;
import com.tedros.fxapi.annotation.control.TTabPane;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.control.TValidator;
import com.tedros.fxapi.annotation.effect.TDropShadow;
import com.tedros.fxapi.annotation.effect.TEffect;
import com.tedros.fxapi.annotation.form.TDetailForm;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.reader.TTextReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.control.TText.TTextStyle;
import com.tedros.fxapi.domain.TFileExtension;
import com.tedros.fxapi.domain.TFileModelType;
import com.tedros.fxapi.domain.THtmlConstant;
import com.tedros.fxapi.domain.TStyleParameter;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.fxapi.property.TSimpleFileProperty;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

@TFormReaderHtml
@TForm(name = "Parceiro", showBreadcrumBar=false)
@TEjbService(serviceName = "IEmpresaParceiraControllerRemote", model=EmpresaParceira.class)
@TListViewPresenter(listViewMinWidth=380, listViewMaxWidth=380,
	paginator=@TPaginator(entityClass = EmpresaParceira.class, serviceName = "IEmpresaParceiraControllerRemote", show=true),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle="Parceiros")))
@TSecurity(	id="SOMOS_PARCEIRO_FORM", 
	appName = "#{somos.name}", moduleName = "Administrativo", viewName = "Parceiros",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class EmpresaParceiraModelView extends TEntityModelView<EmpresaParceira>{
	
	private SimpleLongProperty id;
	
	@TTabPane(tabs = { @TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"textoCadastro", "nome", "contato", "endereco"})), text = "Dados da empresa"), 
			@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"logo"})), text = "Logotipo")
	})
	@TTextReaderHtml(text="Empresa parceira", 
			htmlTemplateForControlValue="<h2 id='"+THtmlConstant.ID+"' name='"+THtmlConstant.NAME+"' style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
			cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
			cssForHtmlBox="", cssForContentValue="color:"+TStyleParameter.PANEL_TEXT_COLOR+";")
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-fieldbox-title", effect=@TEffect(dropShadow=@TDropShadow, parse=true), parse = true))
	@TText(text="Empresa parceira", textAlignment=TextAlignment.LEFT, 
			textStyle = TTextStyle.LARGE)
	private SimpleStringProperty textoCadastro;
	
	@TReaderHtml
	@TLabel(text="Nome")
	@TTextField(maxLength=100, required=true,
	node=@TNode(requestFocus=true, parse = true),
	textInputControl=@TTextInputControl(promptText="Nome Fantasia/Razão Social", parse = true))
	@THBox(	pane=@TPane(children={"nome","cnpj"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="nome", priority=Priority.ALWAYS),
   				   		@TPriority(field="cnpj", priority=Priority.ALWAYS) }))
	private SimpleStringProperty nome;
	
	@TReaderHtml
	@TLabel(text="CNPJ")
	@TMaskField(mask="99.999.999/9999-99")
	private SimpleStringProperty cnpj;
	
	@TReaderHtml
	@TLabel(text="Contato")
	@TTextField(maxLength=100, required=true,
	textInputControl=@TTextInputControl(promptText="Nome da pessoa para contato", parse = true))
	@THBox(	pane=@TPane(children={"contato","telefone","email"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="contato", priority=Priority.ALWAYS),
   				   		@TPriority(field="telefone", priority=Priority.ALWAYS),
   				   		@TPriority(field="email", priority=Priority.ALWAYS) }))
	private SimpleStringProperty contato;
	
	@TReaderHtml
	@TLabel(text="Telefone")
	@TMaskField(mask="(99) 99999-9999", required=true)
	private SimpleStringProperty telefone;
	
	@TReaderHtml
	@TLabel(text="Email (Login no painel)")
	@TTextField(maxLength=100, required=true)
	@TValidator(validatorClass = EmailValidator.class)
	private SimpleStringProperty email;
	
	@TReaderHtml
	@TLabel(text="Endereço")
	@TTextAreaField(maxLength=200, control=@TControl(prefHeight=80, parse = true))
	@THBox(	pane=@TPane(children={"endereco","observacao"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="endereco", priority=Priority.ALWAYS),
   				   		@TPriority(field="observacao", priority=Priority.ALWAYS)}))
	private SimpleStringProperty endereco;
	
	@TReaderHtml
	@TLabel(text="Observação")
	@TTextAreaField(maxLength=300, control=@TControl(prefHeight=80, parse = true))
	private SimpleStringProperty observacao;
	
	@TLabel(text="Logo")
	@TFieldBox(node=@TNode(id="logo", parse=true))
	@TFileField(showImage=true, propertyValueType=TFileModelType.ENTITY,
	maxImageHeight=161, preLoadFileBytes=true, maxImageWidth=161, 
	extensions= {TFileExtension.JPG, TFileExtension.PNG},
	showFilePath=true)
	private TSimpleFileProperty<TFileEntity> logo;
	
	
	public EmpresaParceiraModelView(EmpresaParceira entidade) {
		super(entidade);
	}
	
	@Override
	public void reload(EmpresaParceira model) {
		super.reload(model);
	}
	
		
	@Override
	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	@Override
	public SimpleLongProperty getId() {
		return id;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return this.nome;
	}

	/**
	 * @return the textoCadastro
	 */
	public SimpleStringProperty getTextoCadastro() {
		return textoCadastro;
	}

	/**
	 * @param textoCadastro the textoCadastro to set
	 */
	public void setTextoCadastro(SimpleStringProperty textoCadastro) {
		this.textoCadastro = textoCadastro;
	}

	/**
	 * @return the nome
	 */
	public SimpleStringProperty getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(SimpleStringProperty nome) {
		this.nome = nome;
	}

	/**
	 * @return the cnpj
	 */
	public SimpleStringProperty getCnpj() {
		return cnpj;
	}

	/**
	 * @param cnpj the cnpj to set
	 */
	public void setCnpj(SimpleStringProperty cnpj) {
		this.cnpj = cnpj;
	}

	/**
	 * @return the contato
	 */
	public SimpleStringProperty getContato() {
		return contato;
	}

	/**
	 * @param contato the contato to set
	 */
	public void setContato(SimpleStringProperty contato) {
		this.contato = contato;
	}

	/**
	 * @return the telefone
	 */
	public SimpleStringProperty getTelefone() {
		return telefone;
	}

	/**
	 * @param telefone the telefone to set
	 */
	public void setTelefone(SimpleStringProperty telefone) {
		this.telefone = telefone;
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
	 * @return the endereco
	 */
	public SimpleStringProperty getEndereco() {
		return endereco;
	}

	/**
	 * @param endereco the endereco to set
	 */
	public void setEndereco(SimpleStringProperty endereco) {
		this.endereco = endereco;
	}

	/**
	 * @return the observacao
	 */
	public SimpleStringProperty getObservacao() {
		return observacao;
	}

	/**
	 * @param observacao the observacao to set
	 */
	public void setObservacao(SimpleStringProperty observacao) {
		this.observacao = observacao;
	}

	/**
	 * @return the logo
	 */
	public TSimpleFileProperty<TFileEntity> getLogo() {
		return logo;
	}

	/**
	 * @param logo the logo to set
	 */
	public void setLogo(TSimpleFileProperty<TFileEntity> logo) {
		this.logo = logo;
	}
}
