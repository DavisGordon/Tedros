package com.solidarity.module.acao.model;

import com.solidarity.model.SiteTermo;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.TCodeValue;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.THTMLEditor;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TRadioButtonField;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.reader.TTextReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.control.TText.TTextStyle;
import com.tedros.fxapi.domain.THtmlConstant;
import com.tedros.fxapi.domain.TStyleParameter;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.text.TextAlignment;

@TFormReaderHtml
@TForm(name = "#{form.web.termo.name}", showBreadcrumBar=false)
@TEjbService(serviceName = "ISiteTermoControllerRemote", model=SiteTermo.class)
@TListViewPresenter(paginator=@TPaginator(entityClass = SiteTermo.class, serviceName = "ISiteTermoControllerRemote", show=true),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle="#{form.web.termo.name}")))
@TSecurity(	id="SOLIDARITY_SITETERMO_FORM", 
	appName = "#{app.name}", moduleName = "#{module.manage.campaign}", viewName = "#{form.web.termo.name}",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class SiteTermoModelView extends TEntityModelView<SiteTermo>{
	
	private SimpleLongProperty id;
	
	@TTextReaderHtml(text="#{form.web.termo.name}", 
			htmlTemplateForControlValue="<h2 id='"+THtmlConstant.ID+"' name='"+THtmlConstant.NAME+"' style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
			cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
			cssForHtmlBox="", cssForContentValue="color:"+TStyleParameter.PANEL_TEXT_COLOR+";")
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", parse = true))
	@TText(text="#{text.termo.web}", textAlignment=TextAlignment.LEFT, 
			textStyle = TTextStyle.LARGE)
	private SimpleStringProperty textoCadastro;
	
	@TLabel(text="Status")
	@TReaderHtml(codeValues={@TCodeValue(code = "ATIVADO", value = "#{label.ativado}"), 
			@TCodeValue(code = "DESATIVADO", value = "#{label.desativado}")})
	@THorizontalRadioGroup(alignment=Pos.TOP_LEFT, required=true, spacing=4,
	radioButtons = {@TRadioButtonField(text="#{label.ativado}", userData="ATIVADO"), 
					@TRadioButtonField(text="#{label.desativado}", userData="DESATIVADO")
	})
	private SimpleStringProperty status;
	
	@TReaderHtml
	@THTMLEditor(required=true)
	private SimpleStringProperty descricao;
	
	
	public SiteTermoModelView(SiteTermo entidade) {
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
		return status;
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
	 * @return the descricao
	 */
	public SimpleStringProperty getDescricao() {
		return descricao;
	}


	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(SimpleStringProperty descricao) {
		this.descricao = descricao;
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
