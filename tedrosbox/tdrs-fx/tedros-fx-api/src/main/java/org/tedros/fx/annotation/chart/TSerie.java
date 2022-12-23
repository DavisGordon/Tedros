package org.tedros.fx.annotation.chart;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The XYChart serie
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface TSerie {
	/**
	 * The name
	 */
	public String  name(); 
	/**
	 * The x and y datas
	 */
	public TData[] data() default {};
	
}
