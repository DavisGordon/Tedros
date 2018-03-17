/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 11/11/2013
 */
package com.tedros.fxapi.form;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import com.tedros.core.presenter.ITPresenter;
import com.tedros.fxapi.presenter.filter.TFilterModelView;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public abstract class THBoxFilterForm<F extends TFilterModelView> extends HBox implements ITFilterForm {
	
	final private ObservableMap<String, TFieldBox> fields;
	@SuppressWarnings("unused")
	final private TFilterFormBuilder<F> formBuilder;
	final private F filterModelView;
	
	private ITPresenter presenter;
	
	@SuppressWarnings("unchecked")
	public THBoxFilterForm(F filterModelView) {
		this.fields = FXCollections.observableHashMap();
		this.formBuilder = new TFilterFormBuilder<F>(this, filterModelView);
		this.filterModelView = filterModelView;
		tInitializeForm();
	}
	
	public void addEndSpacer() {
		Region spacer = new Region();
		setHgrow(spacer, Priority.ALWAYS);
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
		setHgrow(fieldBox, Priority.SOMETIMES);
		getChildren().add(fieldBox);
	}
	
	@Override
	public F getFilterModelView() {
		return filterModelView;
	}
	
	public abstract void tInitializeForm();

	@Override
	public void settPresenter(ITPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public ITPresenter gettPresenter() {
		return this.presenter;
	} 
	
}
