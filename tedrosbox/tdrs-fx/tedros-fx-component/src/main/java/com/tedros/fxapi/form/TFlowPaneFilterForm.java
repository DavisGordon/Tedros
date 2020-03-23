/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 11/11/2013
 */
package com.tedros.fxapi.form;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.layout.FlowPane;

import com.tedros.core.presenter.ITPresenter;
import com.tedros.fxapi.presenter.filter.TFilterModelView;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public abstract class TFlowPaneFilterForm<F extends TFilterModelView> extends FlowPane implements ITFilterForm {
	
	final private ObservableMap<String, TFieldBox> fields;
	@SuppressWarnings("unused")
	private TFilterFormBuilder<F> formBuilder;
	final private F filterModelView;
	
	private ITPresenter presenter;
	
	@SuppressWarnings("unchecked")
	public TFlowPaneFilterForm(F filterModelView) {
		this.fields = FXCollections.observableHashMap();
		this.formBuilder = new TFilterFormBuilder<F>(this, filterModelView);
		this.filterModelView = filterModelView;
		tInitializeForm();
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
		getChildren().add(fieldBox);
	}
	
	public abstract void tInitializeForm(); 
	
	@Override
	public TFilterModelView getFilterModelView() {
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
	
	@SuppressWarnings("unchecked")
	@Override
	public void tReloadForm() {
		getChildren().clear();
		this.formBuilder = new TFilterFormBuilder<F>(this, filterModelView);
		tInitializeForm();
	}
	
}
