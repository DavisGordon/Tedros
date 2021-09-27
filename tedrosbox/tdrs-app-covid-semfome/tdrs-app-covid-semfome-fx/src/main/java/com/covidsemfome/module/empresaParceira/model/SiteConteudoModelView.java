package com.covidsemfome.module.empresaParceira.model;

import com.covidsemfome.parceiro.model.SiteConteudo;
import com.tedros.common.model.TFileEntity;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.TCodeValue;
import com.tedros.fxapi.annotation.control.TCheckBoxField;
import com.tedros.fxapi.annotation.control.TComboBoxField;
import com.tedros.fxapi.annotation.control.TContent;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.TFileField;
import com.tedros.fxapi.annotation.control.THTMLEditor;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TNumberSpinnerField;
import com.tedros.fxapi.annotation.control.TRadioButtonField;
import com.tedros.fxapi.annotation.control.TTab;
import com.tedros.fxapi.annotation.control.TTabPane;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
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
import com.tedros.fxapi.annotation.scene.control.TLabeled;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.control.TText.TTextStyle;
import com.tedros.fxapi.domain.TFileExtension;
import com.tedros.fxapi.domain.THtmlConstant;
import com.tedros.fxapi.domain.TStyleParameter;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.fxapi.property.TSimpleFileEntityProperty;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

@TFormReaderHtml
@TForm(name = "Website Conteudo", showBreadcrumBar=false)
@TEjbService(serviceName = "ISiteConteudoControllerRemote", model=SiteConteudo.class)
@TListViewPresenter(
	paginator=@TPaginator(entityClass = SiteConteudo.class, serviceName = "ISiteConteudoControllerRemote", show=true),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle="Website Conteudo")))
@TSecurity(	id="COVSEMFOME_PARCEIRO_WEBCONTEUDO_FORM", 
	appName = "#{app.name}", moduleName = "Administrativo", viewName = "Conteudo Website Parceiros ",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class SiteConteudoModelView extends TEntityModelView<SiteConteudo>{
	
	private SimpleLongProperty id;
	
	@TTextReaderHtml(text="Seção Website Parceiros", 
			htmlTemplateForControlValue="<h2 id='"+THtmlConstant.ID+"' name='"+THtmlConstant.NAME+"' style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
			cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
			cssForHtmlBox="", cssForContentValue="color:"+TStyleParameter.PANEL_TEXT_COLOR+";")
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-fieldbox-title", effect=@TEffect(dropShadow=@TDropShadow, parse=true), parse = true))
	@TText(text="Seção Website Parceiros", textAlignment=TextAlignment.LEFT, 
			textStyle = TTextStyle.LARGE)
	private SimpleStringProperty textoCadastro;
	
	@TReaderHtml
	@TLabel(text="Titulo")
	@TTextField(maxLength=100, required=true,
	node=@TNode(requestFocus=true, parse = true),
	textInputControl=@TTextInputControl(promptText="Nome Fantasia/Razão Social", parse = true))
	@THBox(	pane=@TPane(children={"titulo","showMenu","orientacao","estilo","ordem","status"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="titulo", priority=Priority.ALWAYS),
   				   		@TPriority(field="status", priority=Priority.ALWAYS) }))
	private SimpleStringProperty titulo;
	
	@TLabel(text="Adicionar ao menu:")
	@TCheckBoxField(labeled=@TLabeled(text="Sim", parse = true), 
	control=@TControl(tooltip="Adiciona este titulo como item no menu", parse = true), required=true)
	private SimpleBooleanProperty showMenu;
	
	@TReaderHtml
	@TLabel(text="Orientação")
	@TComboBoxField(items=OrientacaoOptionBuilder.class, required=true)
	private SimpleStringProperty orientacao;
	
	@TReaderHtml
	@TLabel(text="Estilo")
	@TComboBoxField(items=EstiloOptionBuilder.class, required=true)
	private SimpleStringProperty estilo;
	
	@TReaderHtml
	@TLabel(text="Ordem")
	@TNumberSpinnerField(maxValue = 100)
	private SimpleIntegerProperty ordem;
	
	@TReaderHtml(codeValues={@TCodeValue(code = "ATIVADO", value = "Ativado"), 
			@TCodeValue(code = "DESATIVADO", value = "Desativado")})
	@TLabel(text="Status")
	@THorizontalRadioGroup(alignment=Pos.TOP_LEFT, required=true, spacing=4,
	radioButtons = {@TRadioButtonField(text="Ativado", userData="ATIVADO"), 
					@TRadioButtonField(text="Desativado", userData="DESATIVADO")
	})
	private SimpleStringProperty status;
	
	@TTabPane(tabs = { 
			@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"conteudo"})), text = "Conteudo"), 
			@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"image"})), text = "Imagem")
	})
	@THTMLEditor(control=@TControl(prefHeight=380, parse = true))
	private SimpleStringProperty conteudo;
	
	@TLabel(text="Imagem")
	@TFieldBox(node=@TNode(id="image", parse=true))
	@TFileField(showImage=true, preLoadFileBytes=true, extensions= {TFileExtension.JPG, TFileExtension.PNG},
	showFilePath=true)
	private TSimpleFileEntityProperty<TFileEntity> image;
	
	public SiteConteudoModelView(SiteConteudo entidade) {
		super(entidade);
	}
	
	@Override
	public void reload(SiteConteudo model) {
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
		return this.titulo;
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
	 * @return the titulo
	 */
	public SimpleStringProperty getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(SimpleStringProperty titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return the showMenu
	 */
	public SimpleBooleanProperty getShowMenu() {
		return showMenu;
	}

	/**
	 * @param showMenu the showMenu to set
	 */
	public void setShowMenu(SimpleBooleanProperty showMenu) {
		this.showMenu = showMenu;
	}

	/**
	 * @return the orientacao
	 */
	public SimpleStringProperty getOrientacao() {
		return orientacao;
	}

	/**
	 * @param orientacao the orientacao to set
	 */
	public void setOrientacao(SimpleStringProperty orientacao) {
		this.orientacao = orientacao;
	}

	/**
	 * @return the estilo
	 */
	public SimpleStringProperty getEstilo() {
		return estilo;
	}

	/**
	 * @param estilo the estilo to set
	 */
	public void setEstilo(SimpleStringProperty estilo) {
		this.estilo = estilo;
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

	/**
	 * @return the image
	 */
	public TSimpleFileEntityProperty<TFileEntity> getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(TSimpleFileEntityProperty<TFileEntity> image) {
		this.image = image;
	}

	/**
	 * @return the conteudo
	 */
	public SimpleStringProperty getConteudo() {
		return conteudo;
	}

	/**
	 * @param conteudo the conteudo to set
	 */
	public void setConteudo(SimpleStringProperty conteudo) {
		this.conteudo = conteudo;
	}

}
