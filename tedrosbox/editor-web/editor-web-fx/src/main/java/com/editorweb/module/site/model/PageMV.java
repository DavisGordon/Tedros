/**
 * 
 */
package com.editorweb.module.site.model;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.editorweb.model.Content;
import com.tedros.editorweb.model.Domain;
import com.tedros.editorweb.model.HtmlTemplate;
import com.tedros.editorweb.model.Page;
import com.tedros.fxapi.annotation.control.TContent;
import com.tedros.fxapi.annotation.control.TDetailListField;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewCollectionType;
import com.tedros.fxapi.annotation.control.TShowField;
import com.tedros.fxapi.annotation.control.TShowField.TField;
import com.tedros.fxapi.annotation.control.TTab;
import com.tedros.fxapi.annotation.control.TTabPane;
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
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.layout.TRegion;
import com.tedros.fxapi.collections.ITObservableList;
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
@TForm(name = "#{form.pages}")
@TEjbService(serviceName = "ITPageControllerRemote", model=Page.class)
@TListViewPresenter(presenter=@TPresenter(behavior=@TBehavior(saveAllModels=false, saveOnlyChangedModels=false),
		decorator = @TDecorator(buildNewButton=false, buildDeleteButton=false, buildModesRadioButton=false,
		viewTitle="#{view.pages}")))
@TSecurity(	id="TEW_PAGES_FORM", 
	appName = "#{app.tew.name}", moduleName = "#{module.site}", viewName = "#{view.pages}",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class PageMV extends TEntityModelView<Page> {

	
	@TTabPane(tabs = { 
			@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"domain","styleAttr"})), text = "#{label.main.data}"), 
			@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"contents"})), text = "#{label.contents}")
	})
	private SimpleLongProperty id;
	
	
	@TLabel(text="#{label.domain}")
	@TShowField(fields= {@TField(name="name" , label="#{label.name}")})
	@THBox(	pane=@TPane(children={"domain","title","template", "main"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="domain", priority=Priority.ALWAYS),
   				   	@TPriority(field="title", priority=Priority.ALWAYS),
   				   	@TPriority(field="template", priority=Priority.ALWAYS),
   	   				@TPriority(field="main", priority=Priority.ALWAYS) 
	}))
	private SimpleObjectProperty<Domain> domain;
	
	@TLabel(text="#{label.name}")
	@TShowField
	private SimpleStringProperty title;
	
	@TLabel(text="Template")
	@TShowField(fields= {@TField(name="name" , label="#{label.name}")})
	private SimpleObjectProperty<HtmlTemplate> template;

	@TLabel(text="#{label.main.page}")
	@TShowField()
	private SimpleBooleanProperty  main;

	@TLabel(text="#{label.styleattr}")
	@TShowField
	@THBox(	pane=@TPane(children={"styleAttr", "classAttr"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="styleAttr", priority=Priority.ALWAYS),
   				   		@TPriority(field="classAttr", priority=Priority.ALWAYS) }))
	private SimpleStringProperty styleAttr;
	
	@TLabel(text="#{label.classattr}")
	@TShowField
	private SimpleStringProperty classAttr;
	
	@TFieldBox(node=@TNode(id="ct", parse = true))
	@TDetailListField(entityModelViewClass = ContentMV.class, entityClass = Content.class/*, region=@TRegion(prefHeight=390, parse = true)*/)
	@TModelViewCollectionType(modelClass=Content.class, modelViewClass=ContentMV.class)
	private ITObservableList<ContentMV> contents;
	
	public PageMV(Page entity) {
		super(entity);
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return title;
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
	 * @return the contents
	 */
	public ITObservableList<ContentMV> getContents() {
		return contents;
	}

	/**
	 * @param contents the contents to set
	 */
	public void setContents(ITObservableList<ContentMV> contents) {
		this.contents = contents;
	}

	/**
	 * @return the domain
	 */
	public SimpleObjectProperty<Domain> getDomain() {
		return domain;
	}

	/**
	 * @param domain the domain to set
	 */
	public void setDomain(SimpleObjectProperty<Domain> domain) {
		this.domain = domain;
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
