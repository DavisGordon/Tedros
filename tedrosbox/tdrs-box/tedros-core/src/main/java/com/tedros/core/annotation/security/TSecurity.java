package com.tedros.core.annotation.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines a security point to be configured by the user
 * 
 *  @author Davis Gordon
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.FIELD })
public @interface TSecurity {
	
	/**
	 * An unique identifier for this security definition
	 * */
	public String id();
	
	/**
	 * The app name
	 * */
	public String appName();
	
	/**
	 * The module name
	 * */
	public String moduleName() default "";
	
	/**
	 * The view name
	 * */
	public String viewName() default "";
	
	/**
	 * The description of this security definition to show to the user
	 * */
	public String description() default "";
	
	/**
	 * The allowed accesses to be selected by the user for this security definition 
	 * */
	public TAuthorizationType[] allowedAccesses();
	

}
