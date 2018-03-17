/**
 * 
 */
package com.tedros.core.annotation.security;

/**
 * @author Davis Gordon
 *
 */
public enum TAuthorizationType {
	
	APP_ACCESS ("#{tedros.security.app_access}"), 
	MODULE_ACCESS ("#{tedros.security.module_access}"),
	VIEW_ACCESS ("#{tedros.security.view_access}"),
	EDIT ("#{tedros.security.edit}"), 
	READ ("#{tedros.security.read}"), 
	SAVE ("#{tedros.security.save}"), 
	DELETE ("#{tedros.security.delete}"), 
	NEW ("#{tedros.security.new}");
	
	private String value;
	
	private TAuthorizationType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public static TAuthorizationType getFromName(String name){
		for (TAuthorizationType e : values()) {
			if(e.name().equals(name))
				return e;
		}
		return null;
	}
	
}
