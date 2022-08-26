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

import org.tedros.api.parser.ITAnnotationParser;
import org.tedros.fx.annotation.parser.THTMLEditorParser;
import org.tedros.fx.annotation.parser.TRequiredHTMLEditorParser;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.builder.ITControlBuilder;
import org.tedros.fx.builder.ITFieldBuilder;
import org.tedros.fx.builder.THTMLEditorBuilder;
import org.tedros.fx.control.TRequiredHTMLEditor;
import org.tedros.fx.domain.TDefaultValues;

import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.web.HTMLEditor;

/**
 * <pre>
 * Build a {@link org.tedros.fx.control.THTMLEditor} component.
 * 
 * A control that allows for users to edit text, and apply styling to this text. 
 * The underlying data model is HTML, although this is not shown visually to the end-user.
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface THTMLEditor  {
	
	/**
	 *<pre>
	 * The builder of type {@link ITFieldBuilder} for this component.
	 * 
	 * Default value: {@link THTMLEditorBuilder}
	 *</pre> 
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITControlBuilder> builder() default THTMLEditorBuilder.class;
	
	/**
	 * <pre>
	 * The parser class for this annotation.
	 * 
	 * Default value: {TRequiredHTMLEditorParser.class, THTMLEditorParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {TRequiredHTMLEditorParser.class, THTMLEditorParser.class};
	
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
	 * The {@link HTMLEditor} properties.
	 * 
	 * Sets the HTML content of the editor.
	 * 
	 * Default value: <b>HTML editor.</b> 
	 * </pre>
	 * */
	public String html() default "";
	
	/**
	 * <pre>
	 * The {@link HTMLEditor} properties.
	 * 
	 * Show the print and pdf export action buttons .
	 * 
	 * Default value: false
	 * </pre>
	 * */
	
	public boolean showActionsToolBar() default false;
	/**
	 * <pre>
	 * {@link TRequiredHTMLEditor} Class
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

}
