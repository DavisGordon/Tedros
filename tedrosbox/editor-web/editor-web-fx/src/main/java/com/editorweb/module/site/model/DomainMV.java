/**
 * 
 */
package com.editorweb.module.site.model;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.editorweb.model.Domain;
import com.tedros.editorweb.model.Page;
import com.tedros.fxapi.annotation.control.TContent;
import com.tedros.fxapi.annotation.control.TDetailListField;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewCollectionType;
import com.tedros.fxapi.annotation.control.TTab;
import com.tedros.fxapi.annotation.control.TTabPane;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.form.TDetailForm;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
@TForm(name = "#{form.domain}")
@TEjbService(serviceName = "ITDomainControllerRemote", model=Domain.class)
@TListViewPresenter(presenter=@TPresenter(behavior=@TBehavior(saveAllModels=false, saveOnlyChangedModels=false),
		decorator = @TDecorator(buildModesRadioButton=false, viewTitle="#{view.domain}")))
@TSecurity(	id="TEW_DOMAIN_FORM", 
	appName = "#{app.tew.name}", moduleName = "#{module.site}", viewName = "#{view.domain}",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class DomainMV extends TEntityModelView<Domain> {

	private SimpleLongProperty id;
	
	@TLabel(text="#{label.name}")
	@TTextField(maxLength=60, required = true, 
	node=@TNode(requestFocus=true, parse = true))
	@TTabPane(tabs = { 
			@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"name"})), text = "#{label.main.data}"),
			@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"pages"})), text = "#{label.pages}")
	})
	private SimpleStringProperty name;

	@TFieldBox(node=@TNode(id="pg", parse = true))
	@TDetailListField(entityModelViewClass = DomainPageMV.class, entityClass = Page.class)
	@TModelViewCollectionType(modelClass=Page.class, modelViewClass=DomainPageMV.class)
	private ITObservableList<DomainPageMV> pages;
	
	public DomainMV(Domain entity) {
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
	 * @return the pages
	 */
	public ITObservableList<DomainPageMV> getPages() {
		return pages;
	}

	/**
	 * @param pages the pages to set
	 */
	public void setPages(ITObservableList<DomainPageMV> pages) {
		this.pages = pages;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return name;
	}

}
