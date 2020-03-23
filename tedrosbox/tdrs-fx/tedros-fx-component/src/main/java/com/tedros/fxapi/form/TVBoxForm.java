/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 11/11/2013
 */
package com.tedros.fxapi.form;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

import com.tedros.core.model.ITModelView;
import com.tedros.core.presenter.ITPresenter;
import com.tedros.fxapi.descriptor.TFieldDescriptor;
import com.tedros.fxapi.domain.TViewMode;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public abstract class TVBoxForm<M extends ITModelView<?>> 
extends VBox implements ITModelForm<M> {
	
	private final TFormEngine<M, TVBoxForm<M>> formEngine;
	private final TTriggerLoader<M, TVBoxForm<M>> triggerLoader;
	@SuppressWarnings("rawtypes")
	private ITPresenter presenter;
	
	public TVBoxForm(M modelView) {
		this.formEngine = new TFormEngine<M, TVBoxForm<M>>(this, modelView);
		this.triggerLoader = new TTriggerLoader<M, TVBoxForm<M>>(this);
		buildTriggers();
	}
	
	public TVBoxForm(M modelView, boolean readerMode) {
		this.formEngine = new TFormEngine<>(this, modelView, readerMode);
		this.triggerLoader = new TTriggerLoader<>(this);
		buildTriggers();
	}
	
	@SuppressWarnings("rawtypes")
	public TVBoxForm(ITPresenter presenter, M modelView) {
		this.presenter = presenter;
		this.formEngine = new TFormEngine<M, TVBoxForm<M>>(this, modelView);
		this.triggerLoader = new TTriggerLoader<M, TVBoxForm<M>>(this);
		buildTriggers();
	}
	
	@SuppressWarnings("rawtypes")
	public TVBoxForm(ITPresenter presenter, M modelView, boolean readerMode) {
		this.presenter = presenter;
		this.formEngine = new TFormEngine<>(this, modelView, readerMode);
		this.triggerLoader = new TTriggerLoader<>(this);
		buildTriggers();
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
	public ObservableMap<String, TFieldBox> gettFieldBoxMap() {
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
	
}
