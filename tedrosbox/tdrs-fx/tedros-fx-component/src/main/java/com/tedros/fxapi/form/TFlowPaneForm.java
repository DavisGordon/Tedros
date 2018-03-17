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
import javafx.scene.layout.FlowPane;
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

public abstract class TFlowPaneForm<M extends ITModelView<?>> 
extends FlowPane implements ITModelForm<M> {
	
	@SuppressWarnings("rawtypes")
	private ITPresenter presenter;
	
	private final TFormEngine<M, TFlowPaneForm<M>> formEngine;
	private final TTriggerLoader<M, TFlowPaneForm<M>> triggerLoader;
	
	
	public TFlowPaneForm(M modelView) {
		this.formEngine = new TFormEngine<M, TFlowPaneForm<M>>(this, modelView);
		this.triggerLoader = new TTriggerLoader<M, TFlowPaneForm<M>>(this);
		buildTriggers();
	}

	public TFlowPaneForm(M modelView, Boolean readerMode) {
		this.formEngine = new TFormEngine<M, TFlowPaneForm<M>>(this, modelView, readerMode);
		this.triggerLoader = new TTriggerLoader<M, TFlowPaneForm<M>>(this);
		buildTriggers();
	}
	
	@SuppressWarnings("rawtypes")
	public TFlowPaneForm(ITPresenter presenter, M modelView) {
		this.presenter = presenter;
		this.formEngine = new TFormEngine<M, TFlowPaneForm<M>>(this, modelView);
		this.triggerLoader = new TTriggerLoader<M, TFlowPaneForm<M>>(this);
		buildTriggers();
	}
	
	@SuppressWarnings("rawtypes")
	public TFlowPaneForm(ITPresenter presenter, M modelView, boolean readerMode) {
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
	
	@Override
	public TFieldBox gettFieldBox(String fieldName){
		return formEngine.getFieldBox(fieldName);
	}
	
	@Override
	public ObservableMap<String, TFieldBox> gettFieldBoxMap() {
		return formEngine.getFieldBoxMap();
	}
	
	@Override
	public void tAddFormItem(Node fieldBox) {
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
