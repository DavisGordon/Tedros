package org.tedros.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Define the resources bundle to be used by the application 
 * 
 * @author Davis Gordon
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TResourceBundle {
	
	/**
	 * A list name of resource bundle to be used by the application
	 * */
	public String[] resourceName();
}
