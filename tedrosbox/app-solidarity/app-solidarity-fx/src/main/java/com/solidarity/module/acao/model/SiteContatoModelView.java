package com.solidarity.module.acao.model;

import com.solidarity.model.SiteContato;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.TCodeValue;
import com.tedros.fxapi.annotation.control.TCheckBoxField;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TMaskField;
import com.tedros.fxapi.annotation.control.TNumberSpinnerField;
import com.tedros.fxapi.annotation.control.TRadioButtonField;
import com.tedros.fxapi.annotation.control.TTextField;
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
import com.tedros.fxapi.annotation.scene.control.TLabeled;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.control.TText.TTextStyle;
import com.tedros.fxapi.domain.THtmlConstant;
import com.tedros.fxapi.domain.TStyleParameter;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

@TFormReaderHtml
@TForm(name = "#{form.web.contato.name}", showBreadcrumBar=false)
@TEjbService(serviceName = "ISiteContatoControllerRemote", model=SiteContato.class)
@TListViewPresenter(paginator=@TPaginator(entityClass = SiteContato.class, serviceName = "ISiteContatoControllerRemote", show=true),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle="#{form.web.contato.name}")))
@TSecurity(	id="SOLIDARITY_SITECONTATO_FORM", 
	appName = "#{app.name}", moduleName = "#{module.manage.campaign}", viewName = "#{form.web.contato.name}",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class SiteContatoModelView extends TEntityModelView<SiteContato>{
	
	private SimpleLongProperty id;
	
	@TTextReaderHtml(text="#{form.web.contato.name}", 
			htmlTemplateForControlValue="<h2 id='"+THtmlConstant.ID+"' name='"+THtmlConstant.NAME+"' style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
			cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
			cssForHtmlBox="", cssForContentValue="color:"+TStyleParameter.PANEL_TEXT_COLOR+";")
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", parse = true))
	@TText(text="#{text.item.web}", textAlignment=TextAlignment.LEFT, 
			textStyle = TTextStyle.LARGE)
	private SimpleStringProperty textoCadastro;
	
	
	@TReaderHtml
	@TLabel(text="#{label.name}")
	@TTextField(maxLength=60, required=true, node=@TNode(requestFocus=true, parse = true))
	@THBox(	pane=@TPane(children={"nome","cargo"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="nome", priority=Priority.ALWAYS), 
						@TPriority(field="cargo", priority=Priority.ALWAYS)}))
	private SimpleStringProperty nome;
	
	@TReaderHtml
	@TLabel(text="#{label.cargo}")
	@TTextField(maxLength=80, required=true)
	private SimpleStringProperty cargo;
	
	@TReaderHtml
	@TLabel(text="#{label.tel.cel}")
	@TMaskField(mask="(99) 99999-9999")
	@THBox(	pane=@TPane(children={"telefone","whatsapp"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="telefone", priority=Priority.NEVER), 
						@TPriority(field="whatsapp", priority=Priority.NEVER)}))
	private SimpleStringProperty telefone;
	
	@TReaderHtml
	@TCheckBoxField(labeled=@TLabeled(text="Whatsapp", parse = true))
	private SimpleBooleanProperty  whatsapp;
	
	@TReaderHtml
	@TLabel(text="E-mail")
	@TTextField(maxLength=60)
	@THBox(	pane=@TPane(children={"email","ordem"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="email", priority=Priority.ALWAYS), 
						@TPriority(field="ordem", priority=Priority.NEVER)}))
	private SimpleStringProperty email;
	
	@TReaderHtml
	@TLabel(text="#{label.ordem}")
	@TNumberSpinnerField(maxValue = 100)
	private SimpleIntegerProperty ordem;
	
	@TLabel(text="Status")
	@TReaderHtml(codeValues={@TCodeValue(code = "ATIVADO", value = "#{label.ativado}"), 
			@TCodeValue(code = "DESATIVADO", value = "#{label.desativado}")})
	@THorizontalRadioGroup(alignment=Pos.TOP_LEFT, required=true, spacing=4,
	radioButtons = {@TRadioButtonField(text="#{label.ativado}", userData="ATIVADO"), 
					@TRadioButtonField(text="#{label.desativado}", userData="DESATIVADO")
	})
	private SimpleStringProperty status;
	
	
	public SiteContatoModelView(SiteContato entidade) {
		super(entidade);
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
		return nome;
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
	 * @return the cargo
	 */
	public SimpleStringProperty getCargo() {
		return cargo;
	}


	/**
	 * @param cargo the cargo to set
	 */
	public void setCargo(SimpleStringProperty cargo) {
		this.cargo = cargo;
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
	 * @return the whatsapp
	 */
	public SimpleBooleanProperty getWhatsapp() {
		return whatsapp;
	}


	/**
	 * @param whatsapp the whatsapp to set
	 */
	public void setWhatsapp(SimpleBooleanProperty whatsapp) {
		this.whatsapp = whatsapp;
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
	 * @return the ordem
	 */
	public SimpleIntegerProperty getOrdem() {
		return ordem;
	}


	/**
	 * @param ordem the ordem to set
	 */
	public void setOrdem(SimpleIntegerProperty ordem) {
		this.ordem = ordem;
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


}
