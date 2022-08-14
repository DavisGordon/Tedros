package org.tedros.fx.annotation.process;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/**
 * <pre>
 * The {@link org.tedros.fx.process.TModelProcess} to be executed. 
 * </pre>
 * @author Davis Gordon
 * @see {@link TEntityProcess}
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TModelProcess {
	
	/**
	 * <p>
	 * The {@link org.tedros.fx.process.TModelProcess} to be executed.
	 * 
	 *  Default value: org.tedros.fx.process.TModelProcess.class
	 * </p>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends org.tedros.fx.process.TModelProcess> type();
	
}
