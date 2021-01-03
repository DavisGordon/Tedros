/**
 * 
 */
package com.tedros.fxapi.form;

import com.tedros.core.TInternationalizationEngine;

/**
 * @author Davis Gordon
 *
 */
public enum TBuildFormStatus {
	STARTING ("#{tedros.fxapi.status.starting}"),
	BUILDING("#{tedros.fxapi.status.starting}"),
	LOADING("#{tedros.fxapi.status.loading}"),
	FINISHED("#{tedros.fxapi.status.starting}");
	
	private String value;
	
	private TBuildFormStatus(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return TInternationalizationEngine.getInstance(null).getString(value);
	}
}
