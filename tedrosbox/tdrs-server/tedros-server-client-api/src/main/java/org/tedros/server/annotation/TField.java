/**
 * 
 */
package org.tedros.server.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target({ FIELD })
/**
 * The field info
 * 
 * @author Davis Gordon
 *
 */
public @interface TField {

	/**
	 * The field label
	 * */
	String label();
	
	/**
	 * A relevant description for the content
	 * */
	String description() default "";
	
	/**
	 * Sets this field to mandatory
	 * */
	boolean required() default false;
	
	/**
	 * The name of the column or index containing the data for this field.
	 * The default value is the field name.
	 * */
	String column() default "";
	
	
	/**
	 * An example of the data
	 * */
	String example() default "";
	
	/**
	 * The  date pattern
	 * */
	String datePattern() default "";
	
	/**
	 * Sets a field of Date type with the current date   
	 * */
	boolean setCurrentDate() default false;
	
	/**
	 * A max length if applicable
	 * */
	int maxLength() default -1;
	
	/**
	 * For number values specify the correct type
	 * */
	Class<? extends Number> numberType() default Number.class;
	
	/**
	 * An array of possible values 
	 * */
	String[] possibleValues() default {};
	
	/**
	 * The string convert case sensitive
	 * @default TCaseSensitive.NONE
	 * */
	TCaseSensitive caseSensitive() default TCaseSensitive.NONE;
}
