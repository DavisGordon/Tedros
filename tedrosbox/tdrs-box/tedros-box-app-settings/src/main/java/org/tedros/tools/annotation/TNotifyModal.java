/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 08/11/2013
 */
package org.tedros.tools.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.api.parser.ITAnnotationParser;
import org.tedros.fx.annotation.TDefaultValue;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.parser.TVBoxParser;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.builder.ITFieldBuilder;
import org.tedros.tools.control.builder.TNotifyModalBuilder;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * <pre>
 * Build a {@link org.tedros.fx.control.TEditEntitynModal} component.
 * 
 * This component opens a modal that allows a user to edit items. 
 * 
 *  Must be used with ITObservableList 
 * </pre>
 * @author Davis Gordon
 *
 */
@Deprecated
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TNotifyModal  {
	
	/**
	 *<pre>
	 * The builder of type {@link ITFieldBuilder} for this component.
	 * 
	 *  Default value: {@link TNotifyModalBuilder}
	 *</pre> 
	 * */
	public Class<? extends ITFieldBuilder> builder() default TNotifyModalBuilder.class;
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TVBoxParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {TVBoxParser.class};
	 
	
	/**
	 * The list view width 
	 * @default 220
	 * */
	public double width() default 220;
	/**
	 * The list view height 
	 * @default 120
	 * */
	public double height() default 120;
	
	/**
	 * The modal width 
	 * @default 950
	 * */
	public double modalWidth() default 950;
	/**
	 * The modal height 
	 * @default 700
	 * */
	public double modalHeight() default 700;
	
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
	 * The {@link Pane} settings.
	 * </pre>
	 * */
	public TPane pane() default @TPane();

	/**
	* <pre>
	* {@link VBox} Class
	* 
	*  Sets the value of the property spacing. Property description: The amount of vertical space between each child in the vbox.
	* </pre>
	**/
	public double spacing() default TDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION;

	/**
	* <pre>
	* {@link VBox} Class
	* 
	*  Sets the value of the property alignment. Property description: The overall alignment of children within the vbox's width and height.
	* </pre>
	**/
	public Pos alignment() default Pos.CENTER;

	/**
	* <pre>
	* {@link VBox} Class
	* 
	*  Sets the value of the property fillWidth. Property description: Whether or not resizable children will be resized to fill the full width of the vbox or be kept to their preferred width and aligned according to the alignment hpos value.
	* </pre>
	**/
	public boolean fillWidth() default false;
	
}
