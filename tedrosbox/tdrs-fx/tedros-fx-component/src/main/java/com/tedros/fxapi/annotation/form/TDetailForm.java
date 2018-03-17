/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 08/11/2013
 */
package com.tedros.fxapi.annotation.form;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javafx.geometry.Orientation;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TDetailForm  {
	
	public String[] fields();
	public Orientation orientation() default Orientation.VERTICAL;
	
	
	
}
