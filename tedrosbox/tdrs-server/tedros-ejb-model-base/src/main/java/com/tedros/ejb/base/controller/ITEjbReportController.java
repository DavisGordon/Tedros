/**
 * 
 */
package com.tedros.ejb.base.controller;

import com.tedros.ejb.base.model.ITModel;
import com.tedros.ejb.base.result.TResult;

/**
 * @author Davis Gordon
 *
 */
public interface ITEjbReportController<M extends ITModel> {
	
	public TResult<M> process(M model);

}
