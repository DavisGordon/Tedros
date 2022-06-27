/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 11/08/2014
 */
package com.tedros.ejb.base.model;

import java.io.Serializable;
import java.util.List;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author davis.dun
 *
 */
public interface ITReportModel<M extends ITReportItemModel<?>> extends ITModel, Serializable {

	public String getOrderBy();
	
	public String getOrderType();
	
	public List<M> getResult();
}
