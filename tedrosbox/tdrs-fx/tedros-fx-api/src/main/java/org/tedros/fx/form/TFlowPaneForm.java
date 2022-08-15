/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 11/11/2013
 */
package org.tedros.fx.form;

import java.util.List;
import java.util.Map;

import org.tedros.core.model.ITModelView;
import org.tedros.core.presenter.ITPresenter;
import org.tedros.core.repository.TRepository;
import org.tedros.fx.descriptor.TFieldDescriptor;
import org.tedros.fx.domain.TViewMode;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.web.WebView;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */

public class TFlowPaneForm<M extends ITModelView<?>> 
extends FlowPane implements ITModelForm<M> {
	
	@SuppressWarnings("rawtypes")
	private ITPresenter presenter;
	
	private final TFormEngine<M, TFlowPaneForm<M>> formEngine;
	
	public TFlowPaneForm(M modelView) {
		this.formEngine = new TFormEngine<M, TFlowPaneForm<M>>(this, modelView);
		this.formEngine.setEditMode();
	}
	
	public TFlowPaneForm(M modelView, boolean readerMode) {
		this.formEngine = new TFormEngine<>(this, modelView);
		if(readerMode)
			this.formEngine.setReaderMode();
		else
			this.formEngine.setEditMode();
	}
	
	@SuppressWarnings("rawtypes")
	public TFlowPaneForm(ITPresenter presenter, M modelView) {
		this.presenter = presenter;
		this.formEngine = new TFormEngine<M, TFlowPaneForm<M>>(this, modelView);
		this.formEngine.setEditMode();
	}
	
	@SuppressWarnings("rawtypes")
	public TFlowPaneForm(ITPresenter presenter, M modelView, boolean readerMode) {
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
	
	@Override
	public TFieldBox gettFieldBox(String fieldName){
		return formEngine.getFieldBox(fieldName);
	}
	
	@Override
	public Map<String, TFieldBox> gettFieldBoxMap() {
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
		this.gettObjectRepository().clear();
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
	public TSetting gettSetting(){
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
		super.setHgap(8);
		super.setVgap(8);
		setPadding(new Insets(10, 10, 10, 10));
	}
}
