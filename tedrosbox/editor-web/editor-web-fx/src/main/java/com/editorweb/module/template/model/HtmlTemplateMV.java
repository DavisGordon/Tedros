/**
 * 
 */
package com.editorweb.module.template.model;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.editorweb.model.ComponentTemplate;
import com.tedros.editorweb.model.HtmlTemplate;
import com.tedros.editorweb.model.Script;
import com.tedros.editorweb.model.Style;
import com.tedros.fxapi.annotation.control.TContent;
import com.tedros.fxapi.annotation.control.TDetailListField;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewType;
import com.tedros.fxapi.annotation.control.TTab;
import com.tedros.fxapi.annotation.control.TTabPane;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.form.TDetailForm;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.reader.TDetailReaderHtml;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.scene.layout.TRegion;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TFormReaderHtml
@TForm(name = "#{form.htmltemplate}")
@TEjbService(serviceName = "ITHtmlTemplateControllerRemote", model=HtmlTemplate.class)
@TListViewPresenter(presenter=@TPresenter(behavior=@TBehavior(saveAllModels=false, saveOnlyChangedModels=false),
		decorator = @TDecorator(buildModesRadioButton=false, viewTitle="#{view.htmltemplate}")))
@TSecurity(	id="TEW_HTMLTEMPLATE_FORM", 
	appName = "#{app.tew.name}", moduleName = "#{module.template}", viewName = "#{view.htmltemplate}",
			allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
							TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class HtmlTemplateMV extends TEntityModelView<HtmlTemplate> {

	private SimpleLongProperty id;
	
	@TReaderHtml
	@TLabel(text="#{label.name}")
	@TTextField(maxLength=60, required = true, 
	node=@TNode(requestFocus=true, parse = true))
	@THBox(	pane=@TPane(children={"name","autor","version"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="name", priority=Priority.ALWAYS),
   				   		@TPriority(field="autor", priority=Priority.ALWAYS) }))
	private SimpleStringProperty name;

	@TReaderHtml
	@TLabel(text="#{label.autor}")
	@TTextField(maxLength=60)
	private SimpleStringProperty autor;

	@TReaderHtml
	@TLabel(text="#{label.version}")
	@TTextField(maxLength=10)
	private SimpleStringProperty version;
	
	@TReaderHtml
	@TTextAreaField(control=@TControl(prefWidth=250, prefHeight=380, parse = true), wrapText=true)
	@TTabPane(tabs = { 
			@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"code"})), text = "#{label.code}"), 
			@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"scripts"})), text = "Scripts"), 
			@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"styles"})), text = "Styles"), 
			@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"components"})), text = "#{label.components}")
	})
	private SimpleStringProperty code;

	@TFieldBox(node=@TNode(id="sc", parse = true))
	@TDetailReaderHtml(label=@TLabel(text="Scripts"), entityClass=Script.class, modelViewClass=ScriptMV.class)
	@TDetailListField(entityModelViewClass = ScriptMV.class, entityClass = Script.class)
	@TModelViewType(modelClass=Script.class, modelViewClass=ScriptMV.class)
	private ITObservableList<ScriptMV> scripts;
	
	@TFieldBox(node=@TNode(id="st", parse = true))
	@TDetailReaderHtml(label=@TLabel(text="Styles"), entityClass=Style.class, modelViewClass=StyleMV.class)
	@TDetailListField(entityModelViewClass = StyleMV.class, entityClass = Style.class)
	@TModelViewType(modelClass=Style.class, modelViewClass=StyleMV.class)
	private ITObservableList<StyleMV> styles;

	@TFieldBox(node=@TNode(id="ct", parse = true))
	@TDetailReaderHtml(label=@TLabel(text="#{label.components}"), entityClass=ComponentTemplate.class, modelViewClass=ComponentTemplateMV.class)
	@TDetailListField(entityModelViewClass = ComponentTemplateMV.class, entityClass = ComponentTemplate.class, region=@TRegion(prefHeight=390, parse = true))
	@TModelViewType(modelClass=ComponentTemplate.class, modelViewClass=ComponentTemplateMV.class)
	private ITObservableList<ComponentTemplateMV> components;
	
	
	public HtmlTemplateMV(HtmlTemplate entity) {
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


	/**
	 * @return the name
	 */
	public SimpleStringProperty getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(SimpleStringProperty name) {
		this.name = name;
	}


	/**
	 * @return the autor
	 */
	public SimpleStringProperty getAutor() {
		return autor;
	}


	/**
	 * @param autor the autor to set
	 */
	public void setAutor(SimpleStringProperty autor) {
		this.autor = autor;
	}


	/**
	 * @return the version
	 */
	public SimpleStringProperty getVersion() {
		return version;
	}


	/**
	 * @param version the version to set
	 */
	public void setVersion(SimpleStringProperty version) {
		this.version = version;
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
	 * @return the components
	 */
	public ITObservableList<ComponentTemplateMV> getComponents() {
		return components;
	}


	/**
	 * @param components the components to set
	 */
	public void setComponents(ITObservableList<ComponentTemplateMV> components) {
		this.components = components;
	}


	@Override
	public SimpleStringProperty getDisplayProperty() {
		return name;
	}

}
