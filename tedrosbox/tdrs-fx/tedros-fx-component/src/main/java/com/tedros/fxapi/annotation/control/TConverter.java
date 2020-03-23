package com.tedros.fxapi.annotation.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * Specifies a converter type to convert the field 
 * object for a custom node.
 * </pre>
 * @see com.tedros.fxapi.form.TConverter
 * @author Davis Gordon 
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface TConverter {
	
	/**
	 * Sets a custom {@link com.tedros.fxapi.form.TConverter} implementention type.
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends com.tedros.fxapi.form.TConverter> type();
	
	/**
	 * <pre>
	 * Set true to enable the parser execution 
	 * </pre>
	 * */
	public boolean parse();
}
