package org.tedros.fx.annotation.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * Specifies a converter type to convert the field 
 * object for a custom node.
 * </pre>
 * @see org.tedros.fx.form.TConverter
 * @author Davis Gordon 
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface TConverter {
	
	/**
	 * Sets a custom {@link org.tedros.fx.form.TConverter} implementention type.
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends org.tedros.fx.form.TConverter> type();
	
	/**
	 * <pre>
	 * Set true to enable the parser execution 
	 * </pre>
	 * */
	public boolean parse();
}
