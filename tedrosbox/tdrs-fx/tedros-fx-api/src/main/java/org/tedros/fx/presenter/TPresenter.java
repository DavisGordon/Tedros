/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 07/10/2013
 */
package org.tedros.fx.presenter;

import org.tedros.api.presenter.ITPresenter;
import org.tedros.api.presenter.view.ITView;
import org.tedros.api.presenter.view.TViewState;
import org.tedros.core.ITModule;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

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
	 
    @SuppressWarnings("unchecked")
	public final void setView(V view) {
        if (view == null) {
            throw new NullPointerException("view cannot be null.");
        }
 
        if (this.view != null) {
            throw new IllegalStateException("View has already been set.");
        }
 
        this.view = view;
        ChangeListener<TViewState> chl = new ChangeListener<TViewState>() {
			@Override
			public void changed(ObservableValue<? extends TViewState> a, TViewState o, TViewState n) {
				if(n!=null && n.equals(TViewState.READY)) {
					setViewLoaded(true);
					getView().tStateProperty().removeListener(this);
				}
			}
		};
		this.view.tStateProperty().addListener(chl);
    }
    
    @Override
    public void setViewLoaded(boolean loaded) {
    	viewLoadedProperty.setValue(loaded);
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
