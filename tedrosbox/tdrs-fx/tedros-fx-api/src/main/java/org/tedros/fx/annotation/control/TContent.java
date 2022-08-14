/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 08/11/2013
 */
package org.tedros.fx.annotation.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.fx.annotation.form.TDetailForm;

/**
 * A content describe which data to put inside, choose a list of fields
 * using detailForm. 
 *
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface TContent  {
	
	
	/**
	 * List of fields to add inside the content
	 * */
	public TDetailForm detailForm() default @TDetailForm(fields = {""});
}
