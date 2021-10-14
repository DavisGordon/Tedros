/**
 * 
 */
package com.editorweb.module.site.model;

import com.editorweb.module.template.model.HtmlTemplateMV;
import com.editorweb.module.template.model.ScriptMV;
import com.editorweb.module.template.model.StyleMV;
import com.tedros.editorweb.model.HtmlTemplate;
import com.tedros.editorweb.model.Metadata;
import com.tedros.editorweb.model.Page;
import com.tedros.editorweb.model.Script;
import com.tedros.editorweb.model.Style;
import com.tedros.fxapi.annotation.control.TCheckBoxField;
import com.tedros.fxapi.annotation.control.TComboBoxField;
import com.tedros.fxapi.annotation.control.TContent;
import com.tedros.fxapi.annotation.control.TDetailListField;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewCollectionType;
import com.tedros.fxapi.annotation.control.TOptionsList;
import com.tedros.fxapi.annotation.control.TTab;
import com.tedros.fxapi.annotation.control.TTabPane;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.form.TDetailForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TDetailListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TLabeled;
import com.tedros.fxapi.annotation.scene.layout.TRegion;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.presenter.entity.behavior.TDetailCrudViewBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TDetailCrudViewDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TDetailListViewPresenter(presenter=@TPresenter(
behavior = @TBehavior(type = TDetailCrudViewBehavior.class), 
decorator = @TDecorator(type = TDetailCrudViewDecorator.class, buildModesRadioButton=false, viewTitle="#{label.pages}")))
public class DomainPageMV extends TEntityModelView<Page> {

	@TTabPane(tabs = { 
			@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"title","styleAttr"})), text = "#{label.main.data}"), 
			@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"metadatas"})), text = "Meta"), 
			@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"scripts"})), text = "Scripts"), 
			@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"styles"})), text = "Styles")
	})
	private SimpleLongProperty id;
	
	@TLabel(text="#{label.title}")
	@TTextField(maxLength=80, required = true, 
	node=@TNode(requestFocus=true, parse = true))
	@THBox(	pane=@TPane(children={"title", "template", "main"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="title", priority=Priority.ALWAYS),
   				   		@TPriority(field="main", priority=Priority.ALWAYS) }))
	private SimpleStringProperty title;
	
	@TLabel(text="Template")
	@TComboBoxField(firstItemTex="#{label.select}", required=true,
	optionsList=@TOptionsList(entityClass=HtmlTemplate.class, 
			optionModelViewClass=HtmlTemplateMV.class, serviceName = "ITHtmlTemplateControllerRemote"))
	private SimpleObjectProperty<HtmlTemplate> template;

	@TCheckBoxField(labeled=@TLabeled(text="#{label.main.page}", parse = true))
	private SimpleBooleanProperty  main;

	@TLabel(text="#{label.styleattr}")
	@TTextField(maxLength=160)
	@THBox(	pane=@TPane(children={"styleAttr", "classAttr"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="styleAttr", priority=Priority.ALWAYS),
   				   		@TPriority(field="classAttr", priority=Priority.ALWAYS) }))
	
	private SimpleStringProperty styleAttr;
	
	@TLabel(text="#{label.classattr}")
	@TTextField(maxLength=160)
	private SimpleStringProperty classAttr;
	
	@TFieldBox(node=@TNode(id="md", parse = true))
	@TDetailListField(entityModelViewClass = MetadataMV.class, entityClass = Metadata.class, region=@TRegion(prefHeight=390, parse = true))
	@TModelViewCollectionType(modelClass=Metadata.class, modelViewClass=MetadataMV.class)
	private ITObservableList<MetadataMV> metadatas;
	
	@TFieldBox(node=@TNode(id="sc", parse = true))
	@TDetailListField(entityModelViewClass = ScriptMV.class, entityClass = Script.class)
	@TModelViewCollectionType(modelClass=Script.class, modelViewClass=ScriptMV.class)
	private ITObservableList<ScriptMV> scripts;
	
	@TFieldBox(node=@TNode(id="st", parse = true))
	@TDetailListField(entityModelViewClass = StyleMV.class, entityClass = Style.class)
	@TModelViewCollectionType(modelClass=Style.class, modelViewClass=StyleMV.class)
	private ITObservableList<StyleMV> styles;

	public DomainPageMV(Page entity) {
		super(entity);
	}
	
	/**
	 * @return the id
	 */
	public SimpleLongProperty getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return title;
	}

	/**
	 * @return the title
	 */
	public SimpleStringProperty getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(SimpleStringProperty title) {
		this.title = title;
	}

	/**
	 * @return the main
	 */
	public SimpleBooleanProperty getMain() {
		return main;
	}

	/**
	 * @param main the main to set
	 */
	public void setMain(SimpleBooleanProperty main) {
		this.main = main;
	}

	/**
	 * @return the styleAttr
	 */
	public SimpleStringProperty getStyleAttr() {
		return styleAttr;
	}

	/**
	 * @param styleAttr the styleAttr to set
	 */
	public void setStyleAttr(SimpleStringProperty styleAttr) {
		this.styleAttr = styleAttr;
	}

	/**
	 * @return the classAttr
	 */
	public SimpleStringProperty getClassAttr() {
		return classAttr;
	}

	/**
	 * @param classAttr the classAttr to set
	 */
	public void setClassAttr(SimpleStringProperty classAttr) {
		this.classAttr = classAttr;
	}

	/**
	 * @return the metadatas
	 */
	public ITObservableList<MetadataMV> getMetadatas() {
		return metadatas;
	}

	/**
	 * @param metadatas the metadatas to set
	 */
	public void setMetadatas(ITObservableList<MetadataMV> metadatas) {
		this.metadatas = metadatas;
	}

	/**
	 * @return the scripts
	 */
	public ITObservableList<ScriptMV> getScripts() {
		return scripts;
	}

	/**
	 * @param scripts the scripts to set
	 */
	public void setScripts(ITObservableList<ScriptMV> scripts) {
		this.scripts = scripts;
	}

	/**
	 * @return the styles
	 */
	public ITObservableList<StyleMV> getStyles() {
		return styles;
	}

	/**
	 * @param styles the styles to set
	 */
	public void setStyles(ITObservableList<StyleMV> styles) {
		this.styles = styles;
	}

	/**
	 * @return the template
	 */
	public SimpleObjectProperty<HtmlTemplate> getTemplate() {
		return template;
	}

	/**
	 * @param template the template to set
	 */
	public void setTemplate(SimpleObjectProperty<HtmlTemplate> template) {
		this.template = template;
	}

}
