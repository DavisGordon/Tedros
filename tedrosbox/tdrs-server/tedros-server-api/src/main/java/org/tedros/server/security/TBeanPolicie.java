package org.tedros.server.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Ensures that security policies defined in the 
 * application are respected on the server.
 * 
 *  @author Davis Gordon
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.FIELD })
public @interface TBeanPolicie {
	
	/**
	 * The same unique identifier used in the app view security definition
	 * */
	public String id();
	
	/**
	 * The allowed actions that the user must have.
	 * */
	public TAccessPolicie[] policie();
	

}
