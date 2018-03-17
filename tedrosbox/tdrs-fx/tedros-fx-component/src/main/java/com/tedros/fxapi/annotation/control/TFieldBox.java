/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 23/03/2014
 */
package com.tedros.fxapi.annotation.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import com.tedros.fxapi.annotation.parser.ITAnnotationParser;
import com.tedros.fxapi.annotation.parser.TStackPaneParser;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TInsets;
import com.tedros.fxapi.annotation.scene.layout.TRegion;

/**
 * <pre>
 * Config a {@link com.tedros.fxapi.form.TFieldBox} component.
 * </pre>
 *
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TFieldBox {
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TStackPaneParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {TStackPaneParser.class};
	
	
	/**
	 * <pre>
	 * The {@link Node} settings.
	 * </pre>
	 * */
	public TNode node() default @TNode(parse = false);
	
	/**
	 * <pre>
	 * The {@link Region} settings.
	 * </pre>
	 * */
	public TRegion region() default @TRegion(parse = false);
	
	/**
	* <pre>
	* {@link StackPane} Class
	* 
	*  Sets the margin for the child when contained by a stackpane. 
	*  If set, the stackpane will layout the child with the margin space around it.
	* </pre>
	**/
	public TInsets margin() default @TInsets;

	/**
	* <pre>
	* {@link StackPane} Class
	* 
	*  Sets the value of the property alignment. 
	*  
	*  Property description: 
	*  
	*  The default alignment of children within the stackpane's width and height. 
	*  This may be overridden on individual children by setting the child's alignment constraint.
	* </pre>
	**/
	public Pos alignment() default Pos.CENTER;
	
}
