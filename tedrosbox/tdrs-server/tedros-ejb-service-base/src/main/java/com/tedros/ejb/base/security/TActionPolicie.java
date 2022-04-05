/**
 * 
 */
package com.tedros.ejb.base.security;

/**
 * The action policies that the user must have.
 * 
 * @author Davis Gordon
 */
public enum TActionPolicie {
	
	EDIT,
	/**
	 * Define the allow for the read action 
	 * */
	READ,
	
	/**
	 * Define the allow for the save action 
	 * */
	SAVE,
	/**
	 * Define the allow for the delete action 
	 * */
	NEW,
	/**
	 * Define the allow for the new action 
	 * */
	DELETE,
	/**
	 * Define the allow for the search action 
	 * */
	SEARCH;
	
}
