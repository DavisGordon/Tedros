/**
 * 
 */
package com.tedros.fxapi.annotation.filter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface FilterField {
	
	public enum SqlOperator {
		AND, OR;
	}
	
	public String name();
	
	public boolean required();
	
	public SqlOperator sqlOperator();

}
