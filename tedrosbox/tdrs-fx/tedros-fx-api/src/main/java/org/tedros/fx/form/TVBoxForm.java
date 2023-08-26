/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 11/11/2013
 */
package org.tedros.fx.form;

import java.util.List;
import java.util.Map;

import org.tedros.api.descriptor.ITFieldDescriptor;
import org.tedros.api.form.ITFieldBox;
import org.tedros.api.form.ITModelForm;
import org.tedros.api.form.ITSetting;
import org.tedros.api.presenter.ITPresenter;
import org.tedros.api.presenter.view.TViewMode;
import org.tedros.core.model.ITModelView;
import org.tedros.core.repository.TRepository;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TVBoxForm<M extends ITModelView<?>> 
extends VBox implements ITModelForm<M> {
	
	protected final TFormEngine<M, TVBoxForm<M>> formEngine;
	@SuppressWarnings("rawtypes")
	private ITPresenter presenter;
	
	
	public TVBoxForm(M modelView) {
		this.formEngine = new TFormEngine<M, TVBoxForm<M>>(this, modelView);
		this.formEngine.setEditMode();
	}
	
	public TVBoxForm(M modelView, boolean readerMode) {
		this.formEngine = new TFormEngine<>(this, modelView);
		if(readerMode)
			this.formEngine.setReaderMode();
		else
			this.formEngine.setEditMode();
	}
	
	@SuppressWarnings("rawtypes")
	public TVBoxForm(ITPresenter presenter, M modelView) {
		this.presenter = presenter;
		this.formEngine = new TFormEngine<M, TVBoxForm<M>>(this, modelView);
		this.formEngine.setEditMode();
	}
	
	@SuppressWarnings("rawtypes")
	public TVBoxForm(ITPresenter presenter, M modelView, boolean readerMode) {
		this.presenter = presenter;
		this.formEngine = new TFormEngine<>(this, modelView, readerMode);
		if(readerMode)
			this.formEngine.setReaderMode();
		else
			this.formEngine.setEditMode();
	}
	
	
	
	@Override
	public ObservableList<Node> gettFormItens() {
		return formEngine.getFields();
	}
	
	@Override
	public TViewMode gettMode() {
		return this.formEngine.getMode();
	}
	
	public void settReaderMode(){
		formEngine.setReaderMode();
	}
	
	public void settEditMode(){
		formEngine.setEditMode();
	}
	
	public void addEndSpacer() {
		Region spacer = new Region();
		setVgrow(spacer, Priority.ALWAYS);
		getChildren().add(getChildren().size(), spacer);
	}
	
	@Override
	public TFieldBox gettFieldBox(String fieldName){
		return formEngine.getFieldBox(fieldName);
	}
	
	@Override
	public Map<String, ITFieldBox> gettFieldBoxMap() {
		return formEngine.getFieldBoxMap();
	}
	
	@Override
	public void tAddFormItem(final Node fieldBox) {
		if(fieldBox instanceof Accordion)
			setVgrow(fieldBox, Priority.NEVER);
		else
			setVgrow(fieldBox, Priority.SOMETIMES);
		getChildren().add(fieldBox);
	}
	
	@Override
	public M gettModelView() {
		return formEngine.getModelView();
	}
	
	public List<ITFieldDescriptor> gettFieldDescriptorList(){
		return formEngine.getFieldDescriptorList();
	}
	
	@Override
	public void tAddAssociatedObject(String name, Object obj) {
		this.formEngine.addAssociatedObject(name, obj);
	}
	
	@Override
	public Object gettAssociatedObject(String name) {
		return this.formEngine.getAssociatedObject(name);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public ITPresenter gettPresenter() {
		return presenter;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void settPresenter(ITPresenter presenter) {
		this.presenter = presenter;
	}
	
	@Override
	public void tReloadForm() {
		this.formEngine.reloadForm();
	}
	
	@Override
	public WebView gettWebView() {
		return this.formEngine.getWebView();
	}

	@Override
	public TRepository gettObjectRepository() {
		return this.formEngine.getObjectRepository();
	}

	@Override
	public void tDispose() {
		this.parentProperty().addListener((a,o,n)->{
			if(n==null)
				this.getChildren().clear();
		});
		this.formEngine.dispose();
	}

	@Override
	public ReadOnlyBooleanProperty tDisposeProperty() {
		return this.formEngine.disposeProperty();
	}

	@Override
	public ReadOnlyBooleanProperty tLoadedProperty() {
		return this.formEngine.loadedProperty();
	}

	@Override
	public boolean isLoaded() {
		return this.formEngine.loadedProperty().get();
	}

	@Override
	public ITSetting gettSetting(){
		return this.formEngine.getSetting();
	}
	
	@Override
	public void tInitializeForm() {
		formatForm();
	}

	@Override
	public void tInitializeReader() {
		formatForm();
		
	}
	
	private void formatForm() {
		autosize();
		setSpacing(8);
		setPadding(new Insets(10, 10, 10, 10));
		//setMaxHeight(Double.MAX_VALUE);
		//setMaxWidth(Double.MAX_VALUE);
		//addEndSpacer();
	}

}
