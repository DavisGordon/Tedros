package org.somos.module.acao.model;

import org.somos.model.SiteVoluntario;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.ejb.base.model.ITFileBaseModel;
import com.tedros.fxapi.annotation.TCodeValue;
import com.tedros.fxapi.annotation.control.TContent;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.THTMLEditor;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TRadioButtonField;
import com.tedros.fxapi.annotation.control.TSelectImageField;
import com.tedros.fxapi.annotation.control.TTab;
import com.tedros.fxapi.annotation.control.TTabPane;
import com.tedros.fxapi.annotation.form.TDetailForm;
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
import com.tedros.fxapi.domain.TEnvironment;
import com.tedros.fxapi.domain.THtmlConstant;
import com.tedros.fxapi.domain.TStyleParameter;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.text.TextAlignment;

@TFormReaderHtml
@TForm(name = "Site/Voluntario", showBreadcrumBar=false)
@TEjbService(serviceName = "ISiteVoluntarioControllerRemote", model=SiteVoluntario.class)
@TListViewPresenter(paginator=@TPaginator(entityClass = SiteVoluntario.class, serviceName = "ISiteVoluntarioControllerRemote", show=true),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle="Site/Voluntario")))
@TSecurity(	id="SOMOS_SITEVOLUNTARIO_FORM", 
	appName = "#{somos.name}", moduleName = "Gerenciar Campanha", viewName = "Site/Voluntario",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class SiteVoluntarioModelView extends TEntityModelView<SiteVoluntario>{
	
	private SimpleLongProperty id;
	
	@TTextReaderHtml(text="Site/Voluntario", 
			htmlTemplateForControlValue="<h2 id='"+THtmlConstant.ID+"' name='"+THtmlConstant.NAME+"' style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
			cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
			cssForHtmlBox="", cssForContentValue="color:"+TStyleParameter.PANEL_TEXT_COLOR+";")
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", parse = true))
	@TText(text="Item a ser exibido no site", textAlignment=TextAlignment.LEFT, 
			textStyle = TTextStyle.LARGE)
	private SimpleStringProperty textoCadastro;
	
	@TReaderHtml(codeValues={@TCodeValue(code = "ATIVADO", value = "Ativado"), 
			@TCodeValue(code = "DESATIVADO", value = "Desativado")})
	@TLabel(text="Status")
	@THorizontalRadioGroup(alignment=Pos.TOP_LEFT, required=true, spacing=4,
	radioButtons = {@TRadioButtonField(text="Ativado", userData="ATIVADO"), 
					@TRadioButtonField(text="Desativado", userData="DESATIVADO")
	})
	private SimpleStringProperty status;
	
	@TLabel(text="Descrição")
	@TReaderHtml
	@THTMLEditor(/*required=true*/)
	@TTabPane(tabs = { @TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"descricao"})), text = "Conteudo"),
			@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"image"})), text = "Imagem")
			
	})
	private SimpleStringProperty descricao;
	
	@TFieldBox(node=@TNode(id="img", parse = true))
	@TSelectImageField(source=TEnvironment.LOCAL, target=TEnvironment.REMOTE, remoteOwner="somos")
	private SimpleObjectProperty<ITFileBaseModel> image;
	
	
	public SiteVoluntarioModelView(SiteVoluntario entidade) {
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


	/**
	 * @return the image
	 */
	public SimpleObjectProperty<ITFileBaseModel> getImage() {
		return image;
	}


	/**
	 * @param image the image to set
	 */
	public void setImage(SimpleObjectProperty<ITFileBaseModel> image) {
		this.image = image;
	}


}
