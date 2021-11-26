package com.covidsemfome.module.empresaParceira.model;

import com.covidsemfome.module.web.model.ComponentTemplateMV;
import com.covidsemfome.module.web.model.CssClassMV;
import com.covidsemfome.parceiro.model.ComponentTemplate;
import com.covidsemfome.parceiro.model.CssClass;
import com.covidsemfome.parceiro.model.SiteConteudo;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.ejb.base.model.ITFileBaseModel;
import com.tedros.fxapi.annotation.control.TCheckBoxField;
import com.tedros.fxapi.annotation.control.TComboBoxField;
import com.tedros.fxapi.annotation.control.TContent;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.THTMLEditor;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewCollectionType;
import com.tedros.fxapi.annotation.control.TNumberSpinnerField;
import com.tedros.fxapi.annotation.control.TOptionsList;
import com.tedros.fxapi.annotation.control.TPickListField;
import com.tedros.fxapi.annotation.control.TRadioButtonField;
import com.tedros.fxapi.annotation.control.TSelectImageField;
import com.tedros.fxapi.annotation.control.TShowField;
import com.tedros.fxapi.annotation.control.TTab;
import com.tedros.fxapi.annotation.control.TTabPane;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.form.TDetailForm;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.form.TSetting;
import com.tedros.fxapi.annotation.layout.TAccordion;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.layout.TSliderMenu;
import com.tedros.fxapi.annotation.layout.TTitledPane;
import com.tedros.fxapi.annotation.layout.TVBox;
import com.tedros.fxapi.annotation.layout.TVGrow;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.scene.control.TLabeled;
import com.tedros.fxapi.annotation.scene.web.TWebEngine;
import com.tedros.fxapi.annotation.scene.web.TWebView;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.domain.TEnvironment;
import com.tedros.fxapi.domain.TLabelPosition;
import com.tedros.fxapi.domain.TOptionProcessType;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.Priority;

@TSetting(SiteConteudoSetting.class)
@TForm(name = "Website Conteudo", form=SiteConteudoForm.class)
@TEjbService(serviceName = "ISiteConteudoControllerRemote", model=SiteConteudo.class)
@TListViewPresenter(
	paginator=@TPaginator(entityClass = SiteConteudo.class, serviceName = "ISiteConteudoControllerRemote", show=true),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle="Website Conteudo", 
	readerModeTitle="Ver template", buildModesRadioButton=false )))
@TSecurity(	id="COVSEMFOME_PARCEIRO_WEBCONTEUDO_FORM", 
	appName = "#{app.name}", moduleName = "Administrativo", viewName = "Conteudo Website Parceiros ",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT,  
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class SiteConteudoModelView extends TEntityModelView<SiteConteudo>{
	
	
	@TSliderMenu(content = "editor", menu = "selected", menuWidth=390)
	private SimpleLongProperty id;
	
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-fieldbox-info", parse = true))
	@TLabel(text="Editando tag:", position=TLabelPosition.TOP)
	@TShowField
	@TVBox(	pane=@TPane(children={"titulo", "selected", "template"}), spacing=10, fillWidth=true,
	vgrow=@TVGrow(priority={@TPriority(field="title", priority=Priority.NEVER),
			@TPriority(field="selected", priority=Priority.NEVER),
			@TPriority(field="template", priority=Priority.ALWAYS)
	}))
	private SimpleStringProperty selected;
	
	@TReaderHtml
	@TLabel(text="#{label.title}")
	@TTextField(maxLength=100, required=true,
	node=@TNode(requestFocus=true, parse = true))
	@THBox(	pane=@TPane(children={"titulo","ordem"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="titulo", priority=Priority.ALWAYS),
   				   		@TPriority(field="ordem", priority=Priority.NEVER) }))
	private SimpleStringProperty titulo;
	
	@TReaderHtml
	@TLabel(text="#{label.order}")
	@TNumberSpinnerField(maxValue = 100)
	private SimpleIntegerProperty ordem;
	
	@TAccordion(expandedPane="tmplt", node=@TNode(id="contaacc",parse = true), /*region=@TRegion(maxWidth=300, parse = true),*/
			panes={
					@TTitledPane(text="#{label.component}", node=@TNode(id="tmplt",parse = true), 
							expanded=true, fields={"template", "showMenu", "status"}),
					@TTitledPane(text="#{label.css.domain}", fields={"cssClassList"})
				})
	@TLabel(text="#{label.component}")
	@TComboBoxField(optionsList=@TOptionsList(serviceName = "IComponentTemplateControllerRemote", 
	entityClass=ComponentTemplate.class, optionModelViewClass=ComponentTemplateMV.class, 
	optionProcessType=TOptionProcessType.LIST_ALL), required=true)
	private SimpleObjectProperty<ComponentTemplate> template;

	@TCheckBoxField(labeled=@TLabeled(text="#{label.add.menu}", parse = true))
	private SimpleBooleanProperty showMenu;
	
	@TLabel(text="Status")
	@THorizontalRadioGroup(alignment=Pos.TOP_LEFT, required=true, spacing=4,
	radioButtons = {@TRadioButtonField(text="Ativado", userData="ATIVADO"), 
					@TRadioButtonField(text="Desativado", userData="DESATIVADO")
	})
	private SimpleStringProperty status;
	
	@TPickListField(selectedLabel="#{label.selected}", 
			sourceLabel="#{view.cssclass}", width=110, 
			selectionMode=SelectionMode.MULTIPLE,
			optionsList=@TOptionsList(entityClass=CssClass.class,
					optionModelViewClass=CssClassMV.class, serviceName = "ICssClassControllerRemote"))
	@TModelViewCollectionType(modelClass=CssClass.class, modelViewClass=CssClassMV.class)
	private ITObservableList<CssClassMV> cssClassList;
	
	@TTabPane(tabs = { @TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"editor"})), text = "#{label.edit.content}"),
			@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"code"})), text = "#{label.code}"),
			@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"imagem"})), text = "#{label.image}"),
			@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"webview"})), text = "Preview"),
			@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"log"})), text = "Log")
			
	})
	@THTMLEditor(control=@TControl(prefHeight=500, parse = true))
	private SimpleStringProperty editor;
	
	@TWebView(prefHeight=500, 
			engine=@TWebEngine(load="http://localhost:8081/editor-web-webapp/story/edit.html"))
	private SimpleStringProperty webview;

	@TLabel(text="#{label.code}")
	@TTextAreaField(wrapText=true, control=@TControl(prefHeight=400, parse = true))
	private SimpleStringProperty code;
	
	@TFieldBox(node=@TNode(id="img", parse = true))
	@TSelectImageField(source=TEnvironment.REMOTE, target=TEnvironment.REMOTE, remoteOwner="csf")
	private SimpleObjectProperty<ITFileBaseModel> imagem;
	/*@TFieldBox(node=@TNode(id="image", parse=true))
	@TFileField(showImage=true, preLoadFileBytes=true, extensions= {TFileExtension.JPG, TFileExtension.PNG},
	showFilePath=true)*/
	//private TSimpleFileEntityProperty<TFileEntity> image;

	@TLabel(text="Log")
	@TTextAreaField(wrapText=true,textInputControl=@TTextInputControl(editable=false, parse = true), 
	control=@TControl(prefHeight=400, parse = true))
	private SimpleStringProperty log;
	
	public SiteConteudoModelView(SiteConteudo entidade) {
		super(entidade);
		register();
	}
	
	@Override
	public void reload(SiteConteudo model) {
		super.reload(model);
		register();
	}

	/**
	 * 
	 */
	private void register() {
		super.registerProperty("editor", editor);
		super.registerProperty("webview", webview);
		super.registerProperty("selected", selected);
		super.registerProperty("log", log);
		super.registerProperty("imagem", imagem);
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
	 * @return the selected
	 */
	public SimpleStringProperty getSelected() {
		return selected;
	}

	/**
	 * @param selected the selected to set
	 */
	public void setSelected(SimpleStringProperty selected) {
		this.selected = selected;
	}

	/**
	 * @return the template
	 */
	public SimpleObjectProperty<ComponentTemplate> getTemplate() {
		return template;
	}

	/**
	 * @param template the template to set
	 */
	public void setTemplate(SimpleObjectProperty<ComponentTemplate> template) {
		this.template = template;
	}

	/**
	 * @return the cssClassList
	 */
	public ITObservableList<CssClassMV> getCssClassList() {
		return cssClassList;
	}

	/**
	 * @param cssClassList the cssClassList to set
	 */
	public void setCssClassList(ITObservableList<CssClassMV> cssClassList) {
		this.cssClassList = cssClassList;
	}

	/**
	 * @return the webview
	 */
	public SimpleStringProperty getWebview() {
		return webview;
	}

	/**
	 * @param webview the webview to set
	 */
	public void setWebview(SimpleStringProperty webview) {
		this.webview = webview;
	}

	/**
	 * @return the code
	 */
	public SimpleStringProperty getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(SimpleStringProperty code) {
		this.code = code;
	}

	/**
	 * @return the log
	 */
	public SimpleStringProperty getLog() {
		return log;
	}

	/**
	 * @param log the log to set
	 */
	public void setLog(SimpleStringProperty log) {
		this.log = log;
	}

	/**
	 * @return the editor
	 */
	public SimpleStringProperty getEditor() {
		return editor;
	}

	/**
	 * @param editor the editor to set
	 */
	public void setEditor(SimpleStringProperty editor) {
		this.editor = editor;
	}

	/**
	 * @return the imagem
	 */
	public SimpleObjectProperty<ITFileBaseModel> getImagem() {
		return imagem;
	}

	/**
	 * @param imagem the imagem to set
	 */
	public void setImagem(SimpleObjectProperty<ITFileBaseModel> imagem) {
		this.imagem = imagem;
	}


}
