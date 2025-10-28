/**
 * 
 */
package org.tedros.server.controller;

import org.tedros.server.model.ITModel;
import org.tedros.server.result.TResult;
import org.tedros.server.security.TAccessToken;

/**
 * @author Davis Gordon
 *
 */
public interface ITEjbReportController<M extends ITModel> {
	
	public TResult<M> process(TAccessToken token, M model);

}
