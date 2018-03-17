/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 11/08/2014
 */
package com.tedros.fxapi.presenter;

import com.tedros.core.model.ITModelView;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author davis.dun
 *
 */
public interface ITModelPresenter<M extends ITModelView<?>> {
	
	public Class<M> getModelViewClass();
	public void setModelViewClass(Class<M> modelView);
	public void buildForm();
	

}
