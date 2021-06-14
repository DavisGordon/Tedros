/**
 * 
 */
package com.tedros.fxapi.presenter.behavior;

import com.tedros.ejb.base.result.TResult.EnumResult;

/**
 * @author Davis Gordon
 *
 */
public enum TProcessResult {

	RUNNING, SUCCESS, ERROR, WARNING, OUT_OF_DATE, NO_RESULT, FINISHED;
	
	public static TProcessResult get(EnumResult result) {
		switch(result) {
		case SUCESS: return TProcessResult.SUCCESS;
		case ERROR: return TProcessResult.ERROR;
		case WARNING: return TProcessResult.WARNING;
		case OUTDATED: return TProcessResult.OUT_OF_DATE;
		default: return null;
		}
	}
}
