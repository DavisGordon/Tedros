package org.tedros.fx.annotation.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.fx.annotation.parser.TAnnotationParser;
import org.tedros.fx.annotation.parser.TStackPaneParser;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TInsets;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.builder.ITControlBuilder;
import org.tedros.fx.builder.ITGenericBuilder;
import org.tedros.fx.builder.NullObservableListBuilder;
import org.tedros.fx.builder.TPickListFieldBuilder;
import org.tedros.server.entity.ITEntity;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * Build a {@link org.tedros.fx.control.TPickListField} control.
 * 
 * @author Davis Gordon
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TPickListField {

	/**
	 *<pre>
	 * The builder of type {@link ITControlBuilder} for this component.
	 * 
	 *  Default value: {@link TPickListFieldBuilder}
	 *</pre> 
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITControlBuilder> builder() default TPickListFieldBuilder.class;
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TStackPaneParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TAnnotationParser>[] parser() default {TStackPaneParser.class};
	
	/**
	 * <pre>
	 * The {@link Node} properties.
	 * </pre>
	 * */
	public TNode node() default @TNode(parse = false);
	
	/**
	 * <pre>
	 * The {@link Region} properties.
	 * </pre>
	 * */
	public TRegion region() default @TRegion(parse = false);
	
	/**
	 * <pre>
	 * {@link TPickListField} Class
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
	 * {@link TPickListField} Class
	 * 
	 * Sets the ListView width
	 * 
	 * </pre>
	 * */
	public double width() default -1;
	
	/**
	 * <pre>
	 * {@link TPickListField} Class
	 * 
	 * Sets the ListView height
	 * 
	 * </pre>
	 * */
	public double height() default 150;
	
	/**
	 * <pre>
	 * {@link TPickListField} Class
	 * 
	 * Sets the value of the property selectionMode on both ListView.
	 * 
	 * Property description:
	 * 
	 * Specifies the selection mode to use in this selection model. 
	 * The selection mode specifies how many items in the underlying 
	 * data model can be selected at any one time. 
	 * 
	 * By default, the selection mode is SelectionMode.SINGLE
	 * 
	 * </pre>
	 * */
	public SelectionMode selectionMode() default SelectionMode.SINGLE;
	
	/**
	 * <pre>
	 * {@link TPickListField} Class
	 * 
	 * Sets the value of the property sourceLabel.
	 * 
	 * Property description:
	 * 
	 * Set a label to the source list
	 * 
	 * Default value: Empty string.
	 * </pre>
	 * */
	public String sourceLabel() default "";
	
	/**
	 * <pre>
	 * {@link TPickListField} Class
	 * 
	 * Sets the value of the property targetLabel.
	 * 
	 * Property description:
	 * 
	 * Set a label to the target list
	 * 
	 * Default value: Empty string.
	 * </pre>
	 * */
	public String targetLabel() default "";
	

	/**
	* <pre>
	* {@link TPickListField} Class
	* 
	*  Sets the value of the property items in the source list
	*  
	*  Property description: 
	*  
	*  The list of items to show within the source list view 
	* </pre>
	**/
	@SuppressWarnings("rawtypes")
	public Class<? extends ITGenericBuilder<ObservableList>> items() default NullObservableListBuilder.class;
	
	/**
	 * <pre>
	 * {@link TPickListField} Class
	 * 
	 * Sets the value of the property process.
	 * 
	 * Property description:
	 * 
	 * Defines the process to fill the options at source list
	 * 
	 * Default value: Empty string.
	 * </pre>
	 * */
	public TProcess process() default @TProcess(
			query=@TQuery(entity = ITEntity.class),
			service = "");
	
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
