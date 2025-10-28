/**
 * 
 */
package org.tedros.fx.presenter.behavior;

import org.tedros.server.result.TResult.TState;

/**
 * @author Davis Gordon
 *
 */
public enum TProcessResult {

	RUNNING, SUCCESS, ERROR, WARNING, OUT_OF_DATE, NO_RESULT, FINISHED;
	
	public static TProcessResult get(TState result) {
		switch(result) {
		case SUCCESS: return TProcessResult.SUCCESS;
		case ERROR: return TProcessResult.ERROR;
		case WARNING: return TProcessResult.WARNING;
		case OUTDATED: return TProcessResult.OUT_OF_DATE;
		default: return null;
		}
	}
}
