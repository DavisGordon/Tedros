/**
 * 
 */
package com.tedros.core.annotation.security;

/**
 * Authorizations types which can be defined
 * 
 * @author Davis Gordon
 */
public enum TAuthorizationType {
	
	/**
	 * Defines the access to an app
	 * */
	APP_ACCESS ("#{tedros.security.app_access}"),
	/**
	 * Defines the access to a module
	 * */
	MODULE_ACCESS ("#{tedros.security.module_access}"),
	/**
	 * Defines the access to a view
	 * */
	VIEW_ACCESS ("#{tedros.security.view_access}"),
	/**
	 * Define the allow for the edit action 
	 * */
	EDIT ("#{tedros.security.edit}"),
	/**
	 * Define the allow for the read action 
	 * */
	READ ("#{tedros.security.read}"),
	/**
	 * Define the allow for the save action 
	 * */
	SAVE ("#{tedros.security.save}"),
	/**
	 * Define the allow for the delete action 
	 * */
	DELETE ("#{tedros.security.delete}"),
	/**
	 * Define the allow for the new action 
	 * */
	NEW ("#{tedros.security.new}"),
	/**
	 * Define the allow for the export action 
	 * */
	EXPORT ("#{tedros.security.export}"),
	/**
	 * Define the allow for the search action 
	 * */
	SEARCH ("#{tedros.security.search}");
	
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
