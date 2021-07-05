package com.covidsemfome.module.acao.model;

import com.covidsemfome.model.SiteNoticia;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.TCodeValue;
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

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

@TFormReaderHtml
@TForm(name = "Site/Noticias", showBreadcrumBar=false)
@TEjbService(serviceName = "ISiteNoticiaControllerRemote", model=SiteNoticia.class)
@TListViewPresenter(listViewMinWidth=380, listViewMaxWidth=380,
	paginator=@TPaginator(entityClass = SiteNoticia.class, serviceName = "ISiteNoticiaControllerRemote", show=true),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle="Site/Noticias")))
@TSecurity(	id="COVSEMFOME_SITENOTICIA_FORM", 
	appName = "#{app.name}", moduleName = "Gerenciar Campanha", viewName = "Site/Noticias",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class SiteNoticiaModelView extends TEntityModelView<SiteNoticia>{
	
	private SimpleLongProperty id;
	
	@TTextReaderHtml(text="Site/Noticia", 
			htmlTemplateForControlValue="<h2 id='"+THtmlConstant.ID+"' name='"+THtmlConstant.NAME+"' style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
			cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
			cssForHtmlBox="", cssForContentValue="color:"+TStyleParameter.PANEL_TEXT_COLOR+";")
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", parse = true))
	@TText(text="Item a ser exibido no site", textAlignment=TextAlignment.LEFT, 
			textStyle = TTextStyle.LARGE)
	private SimpleStringProperty textoCadastro;
	
	
	@TReaderHtml
	@TLabel(text="Descrição")
	@TTextField(maxLength=100, required=true, node=@TNode(requestFocus=true, parse = true))
	@THBox(	pane=@TPane(children={"descricao", "link", "ordem"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="descricao", priority=Priority.ALWAYS), 
						@TPriority(field="link", priority=Priority.ALWAYS)}))
	private SimpleStringProperty descricao;
	
	@TReaderHtml
	@TLabel(text="Link")
	@TTextField(maxLength=400, required=true)
	private SimpleStringProperty link;
	
	@TReaderHtml
	@TLabel(text="Ordem")
	@TNumberSpinnerField(maxValue = 100)
	private SimpleIntegerProperty ordem;
	
	@TReaderHtml(codeValues={@TCodeValue(code = "ATIVADO", value = "Ativado"), 
			@TCodeValue(code = "DESATIVADO", value = "Desativado")})
	@TLabel(text="Status")
	@THorizontalRadioGroup(alignment=Pos.TOP_LEFT, spacing=4, required=true,
	radioButtons = {@TRadioButtonField(text="Ativado", userData="ATIVADO"), 
					@TRadioButtonField(text="Desativado", userData="DESATIVADO")
	})
	private SimpleStringProperty status;
	
	
	public SiteNoticiaModelView(SiteNoticia entidade) {
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
