/**
 * 
 */
package com.tedros.ejb.base.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target({ FIELD })
/**
 * Define field import rules
 * 
 * @author Davis Gordon
 *
 */
public @interface TFieldImportRule {

	/**
	 * Set required for this field
	 * */
	boolean required();
	
	/**
	 * The file column name or column index that must contain the data for this field
	 * */
	String column();
	
	/**
	 * A relevant description for the content
	 * */
	String description();
	
	/**
	 * The pattern for Date column
	 * */
	String columnDateExample() default "";
	
	/**
	 * The convert date pattern
	 * */
	String datePattern() default "";
	
	/**
	 * Define a date field to be set with the current date   
	 * */
	boolean setCurrentDate() default false;
	
	/**
	 * A max length if applicable
	 * */
	int maxLength() default -1;
}
