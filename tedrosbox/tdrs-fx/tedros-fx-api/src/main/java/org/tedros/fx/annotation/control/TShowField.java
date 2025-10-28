package org.tedros.fx.annotation.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.api.parser.ITAnnotationParser;
import org.tedros.fx.annotation.parser.TStackPaneParser;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TInsets;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.builder.ITFieldBuilder;
import org.tedros.fx.builder.TShowFieldBuilder;
import org.tedros.fx.domain.TDateStyle;
import org.tedros.fx.domain.TLabelPosition;
import org.tedros.fx.domain.TLayoutType;
import org.tedros.fx.domain.TTimeStyle;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * Build the {@link org.tedros.fx.control.TShowField} component 
 * to read and show a field value.
 * 
 * @author Davis Gordon
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface TShowField {
	
	/**
	 * The field to read and show
	 * */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.ANNOTATION_TYPE)
	public @interface TField{
		/**
		 * The field name, empty string will refer the field object.
		 * */
		String name() default "";
		/**
		 * <pre>
		 * Set a date pattern for Date type or a mask for String type.
		 * 
		 * Apply a mask:.
		 * 
		 * A = Only alpha characters [A - Z]
		 * # = Any character, alfa, numeric or symbol
		 * 9 = Only numeric characters [0-9]
		 * 
		 * Example:
		 * 
		 * 999.999.999-99 
		 * 99.999.999/9999-99
		 * AAA-9999 
		 * (99) 9999-99999
		 * [###] A999
		 * </pre>
		 * 
		 * @author Davis Gordon
		 * */ 
		String mask() default "";
		
		/**
		 * Sets a string format 
		 * */
		String format() default "";
		
		/**
		 * The date style
		 * */
		TDateStyle dateStyle() default TDateStyle.DEFAULT;
		
		/**
		 * The time style
		 * */
		TTimeStyle timeStyle() default TTimeStyle.NONE;
		
		/**
		 * The field label
		 * */
		String label() default "";
		/**
		 * The label position
		 * */
		TLabelPosition labelPosition() default TLabelPosition.DEFAULT;
		
		/**
		 * The TConverter&ltObject,String&gt to be applied
		 * */
		@SuppressWarnings("rawtypes")
		Class<? extends org.tedros.fx.converter.TConverter> converter() default org.tedros.fx.converter.TConverter.class;
	};

	/**
	 *<pre>
	 * The builder of type {@link ITFieldBuilder} for this component.
	 * 
	 *  Default value: {@link TShowFieldBuilder}
	 *</pre> 
	 * */
	public Class<? extends ITFieldBuilder> builder() default TShowFieldBuilder.class;
	
	
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {TStackPaneParser.class};
	
	/**
	 * The layout type.
	 * */
	public TLayoutType layout() default TLayoutType.HBOX;
	
	/**
	 * The fields to read and show
	 * */
	public TField[] fields() default {@TField()};
	
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
	public Pos alignment() default Pos.CENTER_LEFT;
}
