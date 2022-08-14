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
import org.tedros.core.module.TObjectRepository;
import org.tedros.core.presenter.ITPresenter;
import org.tedros.fx.descriptor.TFieldDescriptor;
import org.tedros.fx.domain.TViewMode;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */

public class THBoxForm<M extends ITModelView<?>> 
extends HBox implements ITModelForm<M> {
	
	@SuppressWarnings("rawtypes")
	private ITPresenter presenter;
	
	private final TFormEngine<M, THBoxForm<M>> formEngine;

	public THBoxForm(M modelView) {
		this.formEngine = new TFormEngine<M, THBoxForm<M>>(this, modelView);
		this.formEngine.setEditMode();
	}
	
	public THBoxForm(M modelView, boolean readerMode) {
		this.formEngine = new TFormEngine<>(this, modelView);
		if(readerMode)
			this.formEngine.setReaderMode();
		else
			this.formEngine.setEditMode();
	}
	
	@SuppressWarnings("rawtypes")
	public THBoxForm(ITPresenter presenter, M modelView) {
		this.presenter = presenter;
		this.formEngine = new TFormEngine<M, THBoxForm<M>>(this, modelView);
		this.formEngine.setEditMode();
	}
	
	@SuppressWarnings("rawtypes")
	public THBoxForm(ITPresenter presenter, M modelView, boolean readerMode) {
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
		setHgrow(spacer, Priority.ALWAYS);
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

	
	public void tAddFormItem(Node fieldBox) {
		setHgrow(fieldBox, Priority.SOMETIMES);
		getChildren().add(fieldBox);
	}
	
	
	public final M gettModelView() {
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
	public TObjectRepository gettObjectRepository() {
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
		super.setSpacing(8);
		setPadding(new Insets(10, 10, 10, 10));
	}
}
