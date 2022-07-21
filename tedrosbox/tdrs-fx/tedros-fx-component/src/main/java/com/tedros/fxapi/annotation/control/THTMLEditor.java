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

import com.tedros.fxapi.annotation.parser.ITAnnotationParser;
import com.tedros.fxapi.annotation.parser.THTMLEditorParser;
import com.tedros.fxapi.annotation.parser.TRequiredHTMLEditorParser;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.builder.ITControlBuilder;
import com.tedros.fxapi.builder.ITFieldBuilder;
import com.tedros.fxapi.builder.THTMLEditorBuilder;
import com.tedros.fxapi.control.TRequiredHTMLEditor;
import com.tedros.fxapi.domain.TDefaultValues;

import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.web.HTMLEditor;

/**
 * <pre>
 * Build a {@link com.tedros.fxapi.control.THTMLEditor} component.
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
