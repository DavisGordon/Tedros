/**
 * 
 */
package com.covidsemfome.module.web.model;

import com.covidsemfome.parceiro.model.ComponentTemplate;
import com.covidsemfome.parceiro.model.CssClass;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.control.TContent;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewCollectionType;
import com.tedros.fxapi.annotation.control.TOptionsList;
import com.tedros.fxapi.annotation.control.TPickListField;
import com.tedros.fxapi.annotation.control.TTab;
import com.tedros.fxapi.annotation.control.TTabPane;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.form.TDetailForm;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
@TForm(name = "#{label.components}", showBreadcrumBar=true)
@TEjbService(serviceName = "IComponentTemplateControllerRemote", model=ComponentTemplate.class)
@TPresenter(decorator = @TDecorator(viewTitle="#{label.components}"),
behavior=@TBehavior(saveAllModels=false, runNewActionAfterSave=true))
@TSecurity(	id="COVSEMFOME_COMPTEMP_FORM", 
	appName = "#{app.name}", moduleName = "Administrativo", viewName = "#{label.components}",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class ComponentTemplateMV extends TEntityModelView<ComponentTemplate> {

	private SimpleLongProperty id;
	
	@TReaderHtml
	@TLabel(text="#{label.name}")
	@TTextField(maxLength=60, required = true, 
	node=@TNode(requestFocus=true, parse = true))
	private SimpleStringProperty name;

	
	@TTextAreaField(control=@TControl(prefWidth=250, prefHeight=200, parse = true), wrapText=true)
	@TTabPane(tabs = { 
			@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"code"})), text = "#{label.code}"), 
			@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"cssClassList"})), text = "#{view.cssclass}")
	})
	private SimpleStringProperty code;
	
	@TPickListField(selectedLabel="#{label.selected}", 
			sourceLabel="#{view.cssclass}", 
			optionsList=@TOptionsList(entityClass=CssClass.class,
						optionModelViewClass=CssClassMV.class, serviceName = "ICssClassControllerRemote"))
	@TModelViewCollectionType(modelClass=CssClass.class, modelViewClass=CssClassMV.class)
	private ITObservableList<CssClassMV> cssClassList;

	
	public ComponentTemplateMV(ComponentTemplate entity) {
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


	@Override
	public SimpleStringProperty getDisplayProperty() {
		return name;
	}

}
