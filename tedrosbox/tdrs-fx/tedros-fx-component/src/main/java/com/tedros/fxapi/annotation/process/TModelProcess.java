package com.tedros.fxapi.annotation.process;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/**
 * <pre>
 * The {@link com.tedros.fxapi.process.TModelProcess} to be executed. 
 * </pre>
 * @author Davis Gordon
 * @see {@link TEntityProcess}
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TModelProcess {
	
	/**
	 * <p>
	 * The {@link com.tedros.fxapi.process.TModelProcess} to be executed.
	 * 
	 *  Default value: com.tedros.fxapi.process.TModelProcess.class
	 * </p>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends com.tedros.fxapi.process.TModelProcess> type();
	
}
