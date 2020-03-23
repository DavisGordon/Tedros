/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 11/11/2013
 */
package com.tedros.fxapi.form;

import java.lang.reflect.InvocationTargetException;

import com.tedros.core.model.ITFilterModelView;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.exception.TException;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class TFilterViewFormBuilder<F extends ITFilterModelView> {
	
	private F filterView;
	
	public static TFilterViewFormBuilder<?> create(){
		return new TFilterViewFormBuilder<ITFilterModelView>();
	}
	
	public TFilterViewFormBuilder<?> filterView(F filterView) {
		this.filterView = filterView;
		return this;
		
	}
			
	@SuppressWarnings({ "unchecked" })
	public ITFilterForm<F> build() throws TException {
		try {
			Class clazz = TDefaultForm.class;
			TForm tForm = this.filterView.getClass().getAnnotation(TForm.class);
			if(tForm!=null)
				clazz = tForm.form();
			if(clazz!=null)
				return (ITFilterForm<F>) clazz.getConstructor(filterView.getClass()).newInstance(filterView);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new TException(e);
		}
		return null;
		
		
	}
}
