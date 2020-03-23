/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 07/10/2013
 */
package com.tedros.fxapi.presenter;

import java.util.List;

import com.tedros.fxapi.exception.TValidatorException;
import com.tedros.fxapi.form.ITFilterForm;
import com.tedros.fxapi.presenter.filter.TFilterModelView;
import com.tedros.fxapi.presenter.filter.validator.TFilterValidator;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.fxapi.presenter.view.TFilterCrudView;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public abstract class TFilterCrudPresenter<V extends TFilterCrudView, M extends TEntityModelView<?>, F extends TFilterModelView>
extends TCrudPresenter<V, M> {
	
	private Class<? extends ITFilterForm<F>> filterFormClass;
	
	public TFilterCrudPresenter(){
		super();
	}
	
	public TFilterCrudPresenter(V view, M model){
		super(view, model);
	}
	
    @SuppressWarnings("unchecked")
	public void validateFilter(final F tFilterView) throws Exception, TValidatorException {
		TFilterValidator<F> validator = new TFilterValidator<>();
		List<com.tedros.fxapi.presenter.filter.validator.TValidatorResult<F>> lst = validator.validate(tFilterView);
		
		if(lst.size()>0)
			throw new TValidatorException(lst);
	}
    
	public final Class<? extends ITFilterForm<F>> getFilterFormClass() {
		return filterFormClass;
	}

	public final void setFilterFormClass(
			Class<? extends ITFilterForm<F>> filterFormClass) {
		this.filterFormClass = filterFormClass;
	}
    

}
