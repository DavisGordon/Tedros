/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 08/11/2013
 */
package org.tedros.fx.annotation.layout;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.api.parser.ITAnnotationParser;
import org.tedros.api.presenter.view.TViewMode;
import org.tedros.fx.annotation.parser.TFieldSetParser;
import org.tedros.fx.annotation.parser.TStackPaneParser;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TInsets;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.builder.ITLayoutBuilder;
import org.tedros.fx.builder.TFieldSetBuilder;
import org.tedros.fx.builder.TVBoxBuilder;
import org.tedros.fx.domain.TLayoutType;

import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * <pre>
 * Build a {@link org.tedros.fx.layout.TFieldSet}.
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
	public Class<? extends ITLayoutBuilder<org.tedros.fx.layout.TFieldSet>> builder() default TFieldSetBuilder.class;
	
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
	* {@link org.tedros.fx.layout.TFieldSet} Class
	* 
	*  Define the layout type to organize the contents
	*  
	*  Default value: TLayoutType.FLOWPANE
	* </pre>
	**/
	public TLayoutType layoutType() default TLayoutType.FLOWPANE;
	
	/**
	 * <pre>
	 * An array of fields name to add in the {@link org.tedros.fx.layout.TFieldSet}
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
