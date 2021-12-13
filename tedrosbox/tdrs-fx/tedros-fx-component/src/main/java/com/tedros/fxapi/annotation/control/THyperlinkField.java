/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 08/11/2013
 */
package com.tedros.fxapi.annotation.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tedros.fxapi.annotation.parser.TAnnotationParser;
import com.tedros.fxapi.annotation.parser.THyperlinkParser;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TButtonBase;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.scene.control.TLabeled;
import com.tedros.fxapi.builder.ITControlBuilder;
import com.tedros.fxapi.builder.TBaseEventHandlerBuilder;
import com.tedros.fxapi.builder.THyperlinkFieldBuilder;
import com.tedros.fxapi.domain.TDefaultValues;

import javafx.scene.Node;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Control;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Labeled;

/**
 * <pre>
 * Build a {@link Hyperlink} component.
 * 
 * Example:
 * 
 * <i>@</i>THyperlinkField(labeled = <i>@</i>TLabeled(text="Logout", 
 * font=<i>@</i>TFont(size=4, weight=FontWeight.BOLD), parse = true),
 * buttonBase=<i>@</i>TButtonBase(onAction=LogoutEventBuilder.class))
 * private SimpleStringProperty logout;
 * 
 * The event example:
 * 
 * public class LogoutEventBuilder extends TBaseEventHandlerBuilder&ltActionEvent&gt{
 * 		<i>@</i>Override
 * 		public EventHandler&ltActionEvent&gt build() {
 * 			return  e -> {
 * 				//code here
 * 				...
 * 			};
 * 		}
 * }
 * </pre>
 * @see TBaseEventHandlerBuilder
 * @author Davis Gordon
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface THyperlinkField  {
	
	/**
	 *<pre>
	 * The builder of type {@link ITControlBuilder} for this component.
	 * 
	 *  Default value: {@link THyperlinkFieldBuilder}
	 *</pre> 
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITControlBuilder> builder() default THyperlinkFieldBuilder.class;
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {THyperlinkParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TAnnotationParser>[] parser() default {THyperlinkParser.class};
	
	/**
	 * <pre>
	 * The {@link Node} properties.
	 * </pre>
	 * */
	public TNode node() default @TNode(parse = false);
	
	/**
	 * <pre>
	 * The {@link Control} properties.
	 * 
	 * Default value: @TControl(prefWidth=250) 
	 * </pre>
	 * */
	public TControl control() default @TControl(prefWidth=TDefaultValues.LABEL_WIDTH, parse = true);
	
	/**
	 * <pre>
	 * The {@link Labeled} properties.
	 * </pre>
	 * */
	public TLabeled labeled();
	
	/**
	 * <pre>
	 * The {@link ButtonBase} properties.
	 * </pre>
	 * */
	public TButtonBase buttonBase() default @TButtonBase();
	
	/**
	* <pre>
	* {@link Hyperlink} Class
	* 
	*  Sets the value of the property visited. 
	*  Property description: Indicates whether this link has already been "visited".
	*  
	*  Default: true
	* </pre>
	**/
	public boolean visited() default true;
}
