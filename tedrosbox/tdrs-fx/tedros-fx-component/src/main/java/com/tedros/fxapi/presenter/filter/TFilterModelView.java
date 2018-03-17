/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 24/02/2014
 */
package com.tedros.fxapi.presenter.filter;

import com.tedros.core.model.ITFilterModelView;
import com.tedros.ejb.base.model.ITModel;
import com.tedros.fxapi.presenter.model.TModelView;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public abstract class TFilterModelView<M extends ITModel> extends TModelView<M> implements ITFilterModelView<M>{
	
	public TFilterModelView(M modelView) {
		super(modelView);
	}
	
	@SuppressWarnings("unchecked")
	public void cleanFields(){
		try {
			reload((M) model.getClass().newInstance());
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
}
