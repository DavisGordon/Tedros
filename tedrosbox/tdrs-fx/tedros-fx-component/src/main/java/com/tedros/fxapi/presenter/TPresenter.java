/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 07/10/2013
 */
package com.tedros.fxapi.presenter;

import com.tedros.core.ITModule;
import com.tedros.core.presenter.ITPresenter;
import com.tedros.core.presenter.view.ITView;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public abstract class TPresenter<V extends ITView> implements ITPresenter<V> {

	private SimpleBooleanProperty viewLoadedProperty = new SimpleBooleanProperty(false);
	private V view;
	private ITModule module;
	
	public TPresenter(){
		
	}
	
	public TPresenter(V view){
		setView(view);
		initialize();
	}
	
	public TPresenter(V view, ITModule module){
		setView(view);
		initialize();
		this.module = module;
	}
	
	public final V getView() {
		return this.view;
	}
	 
    public final void setView(V view) {
        if (view == null) {
            throw new NullPointerException("view cannot be null.");
        }
 
        if (this.view != null) {
            throw new IllegalStateException("View has already been set.");
        }
 
        this.view = view;
    }
    
    @Override
    public void loadView() {
    	viewLoadedProperty.setValue(true);
    }
    
    @Override
    public boolean isViewLoaded() {
    	return viewLoadedProperty.get();
    }
    
    @Override
    public BooleanProperty viewLoadedProperty() {
    	return viewLoadedProperty;
    }
    
    @Override
    public ITModule getModule() {
    	return module;
    }
    
    @Override
    public void setModule(ITModule module) {
    	this.module = module;   	
    }
    
    public abstract void initialize();    

}
