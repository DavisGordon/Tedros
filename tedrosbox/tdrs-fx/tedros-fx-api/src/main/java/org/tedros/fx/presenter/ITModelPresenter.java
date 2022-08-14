/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 11/08/2014
 */
package org.tedros.fx.presenter;

import org.tedros.core.model.ITModelView;

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
