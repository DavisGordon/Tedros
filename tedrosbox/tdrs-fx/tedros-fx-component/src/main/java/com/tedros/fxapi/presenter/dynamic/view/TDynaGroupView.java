package com.tedros.fxapi.presenter.dynamic.view;

import java.net.URL;

import com.tedros.core.ITModule;
import com.tedros.ejb.base.model.ITModel;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.model.TModelView;

import javafx.collections.ObservableList;

@SuppressWarnings("rawtypes")
public class TDynaGroupView<M extends TModelView> 
extends TDynaView<M> {
	
	private static final String FXML = "TDynamicGroupView.fxml";
    
	public TDynaGroupView(Class<M> modelViewClass) {
		super(new TDynaPresenter<>(modelViewClass), getURL());
	}
	
	public TDynaGroupView(Class<M> modelViewClass, ObservableList<M> models){
		super(new TDynaPresenter<>(modelViewClass, models), getURL());
	}
	
	public TDynaGroupView(Class<M> modelViewClass, Class<? extends ITModel> modelClass, ObservableList<M> models){
		super(new TDynaPresenter<>(modelViewClass, modelClass, models), getURL());
	}
	
	public TDynaGroupView(Class<M> modelViewClass, URL fxmlURL) {
		super(new TDynaPresenter<>(modelViewClass), fxmlURL);
	}
	
	public TDynaGroupView(TDynaPresenter<M> presenter) {
		super(presenter, getURL());
	}
    
	public TDynaGroupView(TDynaPresenter<M> presenter, URL fxmlURL) {
		super(presenter, fxmlURL);
	}

	//
	
	public TDynaGroupView(ITModule module, Class<M> modelViewClass) {
		super(new TDynaPresenter<>(module, modelViewClass), getURL());
	}
	
	public TDynaGroupView(ITModule module, Class<M> modelViewClass, ObservableList<M> models){
		super(new TDynaPresenter<>(module, modelViewClass, models), getURL());
	}
	
	public TDynaGroupView(ITModule module, Class<M> modelViewClass, Class<? extends ITModel> modelClass, ObservableList<M> models){
		super(new TDynaPresenter<>(module, modelViewClass, modelClass, models), getURL());
	}
	
	public TDynaGroupView(ITModule module, Class<M> modelViewClass, URL fxmlURL) {
		super(new TDynaPresenter<>(module, modelViewClass), fxmlURL);
	}
    
	private static URL getURL() {
		return TDynaGroupView.class.getResource(FXML);
	}

	
}
