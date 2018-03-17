/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 11/11/2013
 */
package com.tedros.fxapi.form;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import com.tedros.core.presenter.ITPresenter;
import com.tedros.fxapi.presenter.filter.TFilterModelView;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public abstract class TVBoxFilterForm<F extends TFilterModelView> extends VBox implements ITFilterForm<F> {
	
	final private ObservableMap<String, TFieldBox> fields;
	@SuppressWarnings("unused")
	private TFilterFormBuilder<F> formBuilder;
	final F filterModelView;
	
	private ITPresenter presenter;
	
	public TVBoxFilterForm(F filterModelView) {
		this.filterModelView = filterModelView;
		this.fields = FXCollections.observableHashMap();
		this.formBuilder = new TFilterFormBuilder<F>(this, filterModelView);
		tInitializeForm();
	}

	public void addEndSpacer() {
		Region spacer = new Region();
		setVgrow(spacer, Priority.ALWAYS);
		getChildren().add(getChildren().size(), spacer);
	}
	
	@Override
	public TFieldBox gettFieldBox(String fieldName){
		if(fields.containsKey(fieldName))
			return fields.get(fieldName);
		else
			return null;
	}
	
	@Override
	public ObservableMap<String, TFieldBox> gettFieldBoxMap() {
		return fields;
	}

	@Override
	public void addFieldBox(TFieldBox fieldBox) {
		this.fields.put(fieldBox.gettControlFieldName(), fieldBox);
		setVgrow(fieldBox, Priority.SOMETIMES);
		getChildren().add(fieldBox);
	}
	
	public abstract void tInitializeForm(); 
	
	@Override
	public F getFilterModelView() {
		return filterModelView;
	}

	@Override
	public void settPresenter(ITPresenter presenter) {
		this.presenter = presenter;
	}
	
	@Override
	public ITPresenter gettPresenter() {
		return presenter;
	}
	
	@Override
	public void tReloadForm() {
		getChildren().clear();
		this.formBuilder = new TFilterFormBuilder<F>(this, filterModelView);
		tInitializeForm();
	}
	
}
