/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 24/02/2014
 */
package org.tedros.fx.presenter.filter;

import org.tedros.core.model.ITFilterModelView;
import org.tedros.fx.presenter.model.TModelView;
import org.tedros.server.model.ITModel;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Deprecated
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
