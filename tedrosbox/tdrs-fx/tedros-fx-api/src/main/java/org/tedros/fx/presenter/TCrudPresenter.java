/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 07/10/2013
 */
package org.tedros.fx.presenter;

import java.util.List;

import org.tedros.fx.control.validator.TControlValidator;
import org.tedros.fx.control.validator.TValidatorResult;
import org.tedros.fx.exception.TValidatorException;
import org.tedros.fx.form.ITModelForm;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.fx.presenter.view.TCrudView;

import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.ScrollPaneBuilder;
import javafx.scene.layout.Region;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public abstract class TCrudPresenter<V extends TCrudView, M extends TEntityModelView> 
extends TPresenter<V> implements ITCrudPresenter<M> {
	
	private Class<? extends ITModelForm> formClass;
	private M model;
	private ITModelForm form;
	
	public TCrudPresenter(){
		
	}
	
	public TCrudPresenter(V view){
		setView(view);
		initialize();
	}
	
	public TCrudPresenter(V view, M model){
		setModel(model);
		setView(view);
		initialize();
	}
		   
    @Override
    public void setForm(ITModelForm form) {
    	
    	clearForm();
    	this.form = form;
    	
    	if(this.form.gettPresenter()==null)
    		this.form.settPresenter(this);
    	
    	((Region)form).layout();
    	ScrollPane scroll = ScrollPaneBuilder.create()
    			.id("t-form-scroll")
				.content((Region)form)
				.fitToWidth(true)
				.maxHeight(Double.MAX_VALUE)
				.maxWidth(Double.MAX_VALUE)
				.vbarPolicy(ScrollBarPolicy.AS_NEEDED)
				.hbarPolicy(ScrollBarPolicy.AS_NEEDED)
				.style("-fx-background-color: transparent;")
				.build();
    	
    	getView().gettFormSpace().getChildren().add(scroll);
    	
    }
    
    @Override
    public void clearForm() {
		getView().gettFormSpace().getChildren().clear();
		form = null;
	}
	
    public final M getModel() {
        return this.model;
    }
	 
    public final void setModel(M model) {
        this.model = model;
    }
    
    public final Class<? extends ITModelForm> getFormClass() {
		return formClass;
	}

	public final void setFormClass(Class<? extends ITModelForm> formClass) {
		this.formClass = formClass;
	}    
        
    public void validateModels(final ObservableList<M> modelsViewList) throws Exception, TValidatorException {
		TControlValidator<M> validator = new TControlValidator<>();
		List<TValidatorResult<M>> lst = validator.validate(modelsViewList);
		
		if(lst.size()>0)
			throw new TValidatorException(lst);
	}
    
    public abstract void initialize();

	public final ITModelForm getForm() {
		return form;
	}
    

}
