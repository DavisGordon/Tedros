/**
 * 
 */
package org.tedros.fx.form;

import org.tedros.core.TLanguage;

/**
 * @author Davis Gordon
 *
 */
public enum TBuildFormStatus {
	STARTING ("#{tedros.fxapi.status.starting}"),
	BUILDING("#{tedros.fxapi.status.building}"),
	LOADING("#{tedros.fxapi.status.loading}"),
	FINISHED("#{tedros.fxapi.status.finished}");
	
	private String value;
	
	private TBuildFormStatus(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return TLanguage.getInstance(null).getString(value);
	}
}
