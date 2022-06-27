/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 11/08/2014
 */
package com.tedros.ejb.base.model;

import java.io.Serializable;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author davis.dun
 *
 */
public interface ITReportItemModel<M extends ITModel> extends ITModel, Serializable {

	public M getModel();
}
