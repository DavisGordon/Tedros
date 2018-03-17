/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 08/11/2013
 */
package com.tedros.fxapi.annotation.layout;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import com.tedros.fxapi.annotation.parser.ITAnnotationParser;
import com.tedros.fxapi.annotation.parser.TFieldSetParser;
import com.tedros.fxapi.annotation.parser.TStackPaneParser;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TInsets;
import com.tedros.fxapi.annotation.scene.layout.TRegion;
import com.tedros.fxapi.builder.ITLayoutBuilder;
import com.tedros.fxapi.builder.TFieldSetBuilder;
import com.tedros.fxapi.builder.TVBoxBuilder;
import com.tedros.fxapi.domain.TLayoutType;
import com.tedros.fxapi.domain.TViewMode;

/**
 * <pre>
 * Build a {@link com.tedros.fxapi.layout.TFieldSet}.
 *</pre>
 *
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TFieldSet  {
	
	/**
	 *<pre>
	 * The builder of type {@link ITLayoutBuilder} for this component.
	 * 
	 *  Default value: {@link TVBoxBuilder}
	 *</pre> 
	 * */
	public Class<? extends ITLayoutBuilder<com.tedros.fxapi.layout.TFieldSet>> builder() default TFieldSetBuilder.class;
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TFieldSetParser.class, TStackPaneParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {TFieldSetParser.class, TStackPaneParser.class};
	
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
	* {@link com.tedros.fxapi.layout.TFieldSet} Class
	* 
	*  Define the layout type to organize the contents
	*  
	*  Default value: TLayoutType.FLOWPANE
	* </pre>
	**/
	public TLayoutType layoutType() default TLayoutType.FLOWPANE;
	
	/**
	 * <pre>
	 * An array of fields name to add in the {@link com.tedros.fxapi.layout.TFieldSet}
	 * </pre>
	 * */
	public String[] fields();
	
	/**
	 * <pre>
	 * Define a legend to the fieldset.
	 * </pre> 
	 * */
	public String legend();
	
	/**
	 * <pre>
	 * Skip the field annotated with this annotation.
	 * 
	 * Most used when you whant to add layout component to group fields that is in use by other component.  
	 * </pre>
	 * */
	public boolean skipAnnotatedField() default false;
	
	/**
	* <pre>
	* Specifies the view mode to use this compent.
	* 
	* Set to TMode.READER will build this component only when the user set the view to Reader mode.
	* 
	* Default value:
	* {TMode.EDIT, TMode.READER}
	* </pre>
	**/
	public TViewMode[] mode() default {TViewMode.EDIT, TViewMode.READER}; 
	
}
