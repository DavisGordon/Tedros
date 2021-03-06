package com.tedros.fxapi.annotation.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tedros.fxapi.annotation.parser.ITAnnotationParser;
import com.tedros.fxapi.annotation.parser.TStackPaneParser;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TInsets;
import com.tedros.fxapi.annotation.scene.layout.TRegion;
import com.tedros.fxapi.builder.ITFieldBuilder;
import com.tedros.fxapi.builder.TShowFieldBuilder;
import com.tedros.fxapi.domain.TLabelPosition;
import com.tedros.fxapi.domain.TLayoutType;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * Build the {@link com.tedros.fxapi.control.TShowField} component 
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
		public String name() default "";
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
		public String pattern() default "";
		/**
		 * The field label
		 * */
		public String label() default "";
		/**
		 * The label position
		 * */
		public TLabelPosition labelPosition() default TLabelPosition.DEFAULT;
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
	public TField[] fields() default {};
	
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
