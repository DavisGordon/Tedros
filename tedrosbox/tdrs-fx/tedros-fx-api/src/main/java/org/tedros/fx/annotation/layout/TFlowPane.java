package org.tedros.fx.annotation.layout;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.api.parser.ITAnnotationParser;
import org.tedros.fx.annotation.parser.TFlowPaneMarginParser;
import org.tedros.fx.annotation.parser.TFlowPaneParser;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.builder.ITLayoutBuilder;
import org.tedros.fx.builder.TFlowPaneBuilder;

import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;


/**
 * <pre>
 * Build a {@link FlowPane} layout.
 * 
 * Example:
 * 
 * <i>@</i><strong>TFlowPane</strong>(pane=<i>@</i>TPane(children={"<strong style="color:red;">passField"</strong>, "<strong style="color:blue;">integerField</strong>", "<strong style="color:green;">bigIntegerField</strong>"}))
 *  <i>@</i>TLabel(text="Password:", control=<i>@</i>TControl(prefWidth=500))
 *  <i>@</i>TPasswordField(required=true, maxLength=6)
 *  private SimpleStringProperty <strong style="color:red;">passField;</strong>
 *		
 *  <i>@</i>TLabel(text="Number field", control=<i>@</i>TControl(prefWidth=500))
 *  <i>@</i>TIntegerField(validate=TValidateNumber.GREATHER_THAN_ZERO, control=<i>@</i>TControl(tooltip="Max val: "+Integer.MAX_VALUE))
 *  private SimpleIntegerProperty <strong style="color:blue;">integerField</strong>;
 *		
 *  <i>@</i>TLabel(text="Big number field:", control=<i>@</i>TControl(prefWidth=500))
 *  <i>@</i>TBigIntegerField(validate=TValidateNumber.GREATHER_THAN_ZERO, control=<i>@</i>TControl(tooltip="Max val: infinito"))
 *  private SimpleObjectProperty&lt;BigInteger&gt; <strong style="color:green;">bigIntegerField</strong>;
 * 
 * 
 * Oracle documentation:
 * 
 * FlowPane lays out its children in a flow that 
 * wraps at the flowpane's boundary.
 * A horizontal flowpane (the default) will layout 
 * nodes in rows, wrapping at the flowpane's width. 
 * A vertical flowpane lays out nodes in columns, 
 * wrapping at the flowpane's height. If the flowpane 
 * has a border and/or padding set, the content will 
 * be flowed within those insets.
 * 
 * FlowPane's prefWrapLength property establishes it's 
 * preferred width (for horizontal) or preferred height 
 * (for vertical). Applications should set prefWrapLength 
 * if the default value (400) doesn't suffice. 
 * Note that prefWrapLength is used only for calculating 
 * the preferred size and may not reflect the actual wrapping 
 * dimension, which tracks the actual size of the flowpane.
 * 
 * The alignment property controls how the rows and columns 
 * are aligned within the bounds of the flowpane and defaults 
 * to Pos.TOP_LEFT. It is also possible to control the alignment 
 * of nodes within the rows and columns by setting rowValignment 
 * for horizontal or columnHalignment for vertical.
 * </pre>
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE,ElementType.FIELD})
public @interface TFlowPane {
	
	
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.ANNOTATION_TYPE})
	public @interface TMargin {
		
		@SuppressWarnings("rawtypes")
		public Class<? extends ITAnnotationParser>[] parser() default {TFlowPaneMarginParser.class};
		
		public TFieldInset[] values() default {};
	}
	
	
	/**
	 *<pre>
	 * The builder of type {@link ITLayoutBuilder} for this component.
	 * 
	 *  Default value: {@link TFlowPaneBuilder}
	 *</pre> 
	 * */
	public Class<? extends ITLayoutBuilder<FlowPane>> builder() default TFlowPaneBuilder.class;
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TFlowPaneParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {TFlowPaneParser.class};
	
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
	* {@link FlowPane} Class
	* 
	*  Sets the margin for the child when contained 
	*  by a flowpane. If set, the flowpane will layout 
	*  it out with the margin space around it. 
	*  Setting the value to null will remove the constraint. 
	* </pre>
	**/
	public TMargin margin() default @TMargin();

	/**
	* <pre>
	* {@link FlowPane} Class
	* 
	*  Sets the value of the property orientation. 
	*  
	*  Property description: 
	*  
	*  The orientation of this flowpane. 
	*  A horizontal flowpane lays out children left to right, 
	*  wrapping at the flowpane's width boundary. 
	*  A vertical flowpane lays out children top to bottom,
	*  wrapping at the flowpane's height. 
	*  The default is horizontal.
	* </pre>
	**/
	public Orientation orientation() default Orientation.HORIZONTAL;

	/**
	* <pre>
	* {@link FlowPane} Class
	* 
	*  Sets the value of the property hgap.
	*  
	*  Property description: 
	*  
	*  The amount of horizontal space between each node in 
	*  a horizontal flowpane or the space between columns 
	*  in a vertical flowpane.
	*  
	*  Default value: 10
	* </pre>
	**/
	public double hgap() default 10D;

	/**
	* <pre>
	* {@link FlowPane} Class
	* 
	*  Sets the value of the property vgap. 
	*  
	*  Property description: 
	*  
	*  The amount of vertical space between each node in 
	*  a vertical flowpane or the space between rows in 
	*  a horizontal flowpane.
	*  
	*  Default value: 10
	* </pre>
	**/
	public double vgap() default 10D;

	/**
	* <pre>
	* {@link FlowPane} Class
	* 
	*  Sets the value of the property prefWrapLength. 
	*  
	*  Property description: 
	*  
	*  The preferred width where content should wrap in 
	*  a horizontal flowpane or the preferred height where 
	*  content should wrap in a vertical flowpane. 
	*  This value is used only to compute the preferred size 
	*  of the flowpane and may not reflect the actual width 
	*  or height, which may change if the flowpane is resized 
	*  to something other than its preferred size. 
	*  Applications should initialize this value to define a 
	*  reasonable span for wrapping the content.
	* </pre>
	**/
	public double prefWrapLength() default 400D;

	/**
	* <pre>
	* {@link FlowPane} Class
	* 
	*  Sets the value of the property alignment. 
	*  
	*  Property description: 
	*  
	*  The overall alignment of the flowpane's content 
	*  within its width and height. For a horizontal flowpane,
	*  each row will be aligned within the flowpane's width 
	*  using the alignment's hpos value, and the rows will be 
	*  aligned within the flowpane's height using the alignment's 
	*  vpos value. For a vertical flowpane, each column will be 
	*  aligned within the flowpane's height using the alignment's 
	*  vpos value, and the columns will be aligned within the 
	*  flowpane's width using the alignment's hpos value.
	* </pre>
	**/
	public Pos alignment() default Pos.CENTER;

	/**
	* <pre>
	* {@link FlowPane} Class
	* 
	*  Sets the value of the property columnHalignment. 
	*  Property description: The horizontal alignment of nodes 
	*  within each column of a vertical flowpane. 
	*  The property is ignored for horizontal flowpanes.
	* </pre>
	**/
	public HPos columnHalignment() default HPos.CENTER;

	/**
	* <pre>
	* {@link FlowPane} Class
	* 
	*  Sets the value of the property rowValignment. 
	*  
	*  Property description:
	*  
	*  The vertical alignment of nodes within each row of 
	*  a horizontal flowpane. If this property is set to 
	*  VPos.BASELINE, then the flowpane will always resize 
	*  children to their preferred heights, rather than 
	*  expanding heights to fill the row height. 
	*  The property is ignored for vertical flowpanes.
	* </pre>
	**/
	public VPos rowValignment() default VPos.CENTER;
	
}
