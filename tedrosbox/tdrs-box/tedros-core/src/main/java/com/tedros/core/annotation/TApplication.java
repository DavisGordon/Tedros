package com.tedros.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tedros.app.process.ITProcess;

/**
 * This annotation sets the start up class of the application
 * 
 * @author davis.dun
 * */
@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface TApplication {

	/**
	 * The application name to show
	 * */
	public String name();
	
	/**
	 * An universal unique identifier for the application. 
	 * */
	public String universalUniqueIdentifier();
	
	/***
	 * The application version
	 * */
	public String version() default "";
	
	/**
	 * The application description
	 * */
	public String description() default "";
	
	/**
	 * A list of module of the application
	 * */
	public TModule[] module();
	
	/**
	 * A list of application process to be executed at startup  
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITProcess>[] process() default {};
	
	
	//public boolean secure() default true;
}
