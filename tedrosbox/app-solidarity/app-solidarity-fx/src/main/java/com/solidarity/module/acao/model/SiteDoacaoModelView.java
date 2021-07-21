package com.solidarity.module.acao.model;

import com.solidarity.model.SiteDoacao;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.TCodeValue;
import com.tedros.fxapi.annotation.control.TBigDecimalField;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
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
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.control.TText.TTextStyle;
import com.tedros.fxapi.domain.THtmlConstant;
import com.tedros.fxapi.domain.TStyleParameter;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

@TFormReaderHtml
@TForm(name = "#{form.web.doacao.name}", showBreadcrumBar=false)
@TEjbService(serviceName = "ISiteDoacaoControllerRemote", model=SiteDoacao.class)
@TListViewPresenter(listViewMinWidth=380, listViewMaxWidth=380,
	paginator=@TPaginator(entityClass = SiteDoacao.class, serviceName = "ISiteDoacaoControllerRemote", show=true),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle="#{form.web.doacao.name}")))
@TSecurity(	id="SOLIDARITY_SITEDOACAO_FORM", 
	appName = "#{app.name}", moduleName = "#{module.manage.campaign}", viewName = "#{form.web.doacao.name}",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class SiteDoacaoModelView extends TEntityModelView<SiteDoacao>{
	
	private SimpleLongProperty id;
	
	@TTextReaderHtml(text="#{form.web.doacao.name}", 
			htmlTemplateForControlValue="<h2 id='"+THtmlConstant.ID+"' name='"+THtmlConstant.NAME+"' style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
			cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
			cssForHtmlBox="", cssForContentValue="color:"+TStyleParameter.PANEL_TEXT_COLOR+";")
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", parse = true))
	@TText(text="#{text.item.web}", textAlignment=TextAlignment.LEFT, 
			textStyle = TTextStyle.LARGE)
	private SimpleStringProperty textoCadastro;
	
	
	@TReaderHtml
	@TLabel(text="#{label.descricao}")
	@TTextField(maxLength=100, node=@TNode(requestFocus=true, parse = true), required=true)
	@THBox(	pane=@TPane(children={"descricao","link","valor", "ordem"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="descricao", priority=Priority.ALWAYS), 
						@TPriority(field="link", priority=Priority.ALWAYS), 
   				   		@TPriority(field="valor", priority=Priority.NEVER)}))
	private SimpleStringProperty descricao;
	
	@TReaderHtml
	@TLabel(text="Link")
	@TTextField(maxLength=400)
	private SimpleStringProperty link;
	
	@TReaderHtml
	@TLabel(text = "#{label.valor}")
	@TBigDecimalField()
	private SimpleDoubleProperty valor;
	
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
	
	
	public SiteDoacaoModelView(SiteDoacao entidade) {
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
		return descricao;
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
	 * @return the link
	 */
	public SimpleStringProperty getLink() {
		return link;
	}


	/**
	 * @param link the link to set
	 */
	public void setLink(SimpleStringProperty link) {
		this.link = link;
	}


	/**
	 * @return the valor
	 */
	public SimpleDoubleProperty getValor() {
		return valor;
	}


	/**
	 * @param valor the valor to set
	 */
	public void setValor(SimpleDoubleProperty valor) {
		this.valor = valor;
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


}
