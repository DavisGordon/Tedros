/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 23/03/2014
 */
package org.tedros.fx.annotation.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.api.parser.ITAnnotationParser;
import org.tedros.fx.annotation.parser.TDirectoryFieldParser;
import org.tedros.fx.annotation.parser.TStackPaneParser;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.annotation.scene.control.TInsets;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.builder.ITFieldBuilder;
import org.tedros.fx.builder.TDirectoryFieldBuilder;
import org.tedros.fx.control.action.TEventHandler;
import org.tedros.fx.domain.TDefaultValues;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * <pre>
 * Build a {@link org.tedros.fx.control.TDirectoryField} component.
 * </pre>
 *
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TDirectoryField {
	
	/**
	 *<pre>
	 * The builder of type {@link ITFieldBuilder} for this component.
	 * 
	 *  Default value: {@link TDirectoryFieldBuilder}
	 *</pre> 
	 * */
	public Class<? extends ITFieldBuilder> builder() default TDirectoryFieldBuilder.class;
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TDirectoryFieldParser.class, TStackPaneParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {TDirectoryFieldParser.class, TStackPaneParser.class};
	
	
	/**
	 * <pre>
	 * The {@link Node} settings.
	 * </pre>
	 * */
	public TNode node() default @TNode(parse = false);
	
	/**
	 * <pre>
	 * The {@link Control} settings.
	 * 
	 * Default value: @TControl(prefWidth=250) 
	 * </pre>
	 * */
	public TControl control() default @TControl(prefWidth=TDefaultValues.LABEL_WIDTH, parse = true);
	
	/**
	 * <pre>
	 * The {@link TextInputControl} settings.
	 * </pre>
	 * */
	public TTextInputControl textInputControl() default @TTextInputControl(parse = false);
	
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
	public Pos alignment() default Pos.CENTER_LEFT;
	
	/**
	 * <pre>
	 * {@link org.tedros.fx.control.TDirectoryField} Class
	 * 
	 * Sets the value to the cleanAction property.
	 * 
	 * Property description:
	 * 
	 * Defines the action to the clean button.
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TEventHandler> cleanAction() default TEventHandler.class;
	
	/**
	 * <pre>
	 * {@link org.tedros.fx.control.TDirectoryField} Class
	 * 
	 * Sets the value to the selectAction property.
	 * 
	 * Property description:
	 * 
	 * Defines the action to the select button
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TEventHandler> selectAction() default TEventHandler.class;


	/**
	 * <pre>
	 * {@link TDirectoryField} Class
	 * 
	 * Sets the value of the property required.
	 * 
	 * Property description:
	 * 
	 * Determines with this control will be required.
	 * 
	 * Default value: false.
	 * </pre>
	 * */
	public boolean required() default false;
	
	/**
	 * <pre>
	 * {@link TDirectoryField} Class
	 * 
	 * Sets the value of the property showFilePath.
	 * 
	 * Property description:
	 * 
	 * If true show the directory path.
	 * 
	 * Default value: true.
	 * </pre>
	 * */
	public boolean showFilePath() default true;
	
}
