package org.tedros.fx.annotation.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javafx.scene.control.SingleSelectionModel;

/**
 * <pre>
 * A SelectionModel which enforces the requirement that only 
 * a single index be selected at any given time. 
 * This class exists for controls that allow for pluggable 
 * selection models, but which do not allow for multiple selection.
 * </pre>
 *
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface TSingleSelectionModel {
	
	/**
	* <pre>
	* {@link SingleSelectionModel} Class
	* 
	*  Selects the given index.
	* </pre>
	**/
	public int select() default 0;
	
	/**
	* <pre>
	* {@link SingleSelectionModel} Class
	* 
	*  Selects the first index.
	* </pre>
	**/
	public boolean selectFirst() default true;
	
	/**
	* <pre>
	* {@link SingleSelectionModel} Class
	* 
	*  Selects the last index.
	* </pre>
	**/
	public boolean selectLast() default false;

}
