/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 11/11/2013
 */
package com.tedros.fxapi.form;

import java.util.List;
import java.util.Map;

import com.tedros.core.model.ITModelView;
import com.tedros.core.module.TObjectRepository;
import com.tedros.core.presenter.ITPresenter;
import com.tedros.fxapi.descriptor.TFieldDescriptor;
import com.tedros.fxapi.domain.TViewMode;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.ObservableList;
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
public abstract class TVBoxForm<M extends ITModelView<?>> 
extends VBox implements ITModelForm<M> {
	
	private final TFormEngine<M, TVBoxForm<M>> formEngine;
	private final TTriggerLoader<M, TVBoxForm<M>> triggerLoader = new TTriggerLoader<M, TVBoxForm<M>>(this);
	@SuppressWarnings("rawtypes")
	private ITPresenter presenter;
	private ChangeListener<Boolean> chl = (ob, o, n) -> {
		if(n) buildTriggers();
	};
	
	public TVBoxForm(M modelView) {
		this.formEngine = new TFormEngine<M, TVBoxForm<M>>(this, modelView);
		this.formEngine.loadedProperty().addListener(new WeakChangeListener<>(chl));
		settEditMode();
	}
	
	public TVBoxForm(M modelView, boolean readerMode) {
		this.formEngine = new TFormEngine<>(this, modelView);
		this.formEngine.loadedProperty().addListener(new WeakChangeListener<>(chl));
		if(readerMode)
			settReaderMode();
		else
			settEditMode();
	}
	
	@SuppressWarnings("rawtypes")
	public TVBoxForm(ITPresenter presenter, M modelView) {
		this.presenter = presenter;
		this.formEngine = new TFormEngine<M, TVBoxForm<M>>(this, modelView);
		this.formEngine.loadedProperty().addListener(new WeakChangeListener<>(chl));
		settEditMode();
	}
	
	@SuppressWarnings("rawtypes")
	public TVBoxForm(ITPresenter presenter, M modelView, boolean readerMode) {
		this.presenter = presenter;
		this.formEngine = new TFormEngine<>(this, modelView, readerMode);
		this.formEngine.loadedProperty().addListener(new WeakChangeListener<>(chl));
		if(readerMode)
			settReaderMode();
		else
			settEditMode();
	}
	
	private void buildTriggers() {
		try {
			triggerLoader.buildTriggers();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	public Map<String, TFieldBox> gettFieldBoxMap() {
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
	
	public List<TFieldDescriptor> gettFieldDescriptorList(){
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
		buildTriggers();
	}
	
	@Override
	public WebView gettWebView() {
		return this.formEngine.getWebView();
	}

	@Override
	public TObjectRepository gettObjectRepository() {
		return this.formEngine.getObjectRepository();
	}

	@Override
	public void tDispose() {
		this.gettObjectRepository().clear();
		/*for(TFieldDescriptor fd : this.gettFieldDescriptorList()) {
			String fn = fd.getFieldName();
			Observable ob = this.formEngine.getModelView().getProperty(fn);
			TFieldBox fb = this.gettFieldBox(fn);
			if(ob!=null && fb!=null) {
				
			}
		}*/
	}


	@Override
	public ReadOnlyBooleanProperty tLoadedProperty() {
		return this.formEngine.loadedProperty();
	}

	@Override
	public boolean isLoaded() {
		return this.formEngine.loadedProperty().get();
	}

}
