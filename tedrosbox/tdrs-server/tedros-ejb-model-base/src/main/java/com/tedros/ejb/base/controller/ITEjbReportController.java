/**
 * 
 */
package com.tedros.ejb.base.controller;

import com.tedros.ejb.base.model.ITModel;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.security.TAccessToken;

/**
 * @author Davis Gordon
 *
 */
public interface ITEjbReportController<M extends ITModel> {
	
	public TResult<M> process(TAccessToken token, M model);

}
