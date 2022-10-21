package org.tedros.fx.annotation.layout;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.api.parser.ITAnnotationParser;
import org.tedros.api.presenter.view.TViewMode;
import org.tedros.fx.annotation.parser.TBorderPaneAlignmentParser;
import org.tedros.fx.annotation.parser.TBorderPaneMarginParser;
import org.tedros.fx.annotation.parser.TBorderPaneParser;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.builder.ITLayoutBuilder;
import org.tedros.fx.builder.TBorderPaneBuilder;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;


/**
 * <pre>
 * Build a {@link BorderPane} layout.
 * 
 * Example:
 * 
 * <i>@</i><strong>TBorderPane</strong>(top="<strong style="color:red;">passField</strong>", center="<strong style="color:blue;">integerField</strong>", bottom="<strong style="color:green;">bigIntegerField</strong>")
 *  <i>@</i>TLabel(text="Password:", control=<i>@</i>TControl(prefWidth=500))
 *  <i>@</i>TPasswordField(required=true, maxLength=6)
 *  private SimpleStringProperty <strong style="color:red;">passField;</strong>
 *		
 *  <i>@</i>TLabel(text="Number field", control=<i>@</i>TControl(prefWidth=500))
 *  <i>@</i>TIntegerField(zeroValidation=TZeroValidation.MORE_THAN_ZERO, control=<i>@</i>TControl(tooltip="Max val: "+Integer.MAX_VALUE))
 *  private SimpleIntegerProperty <strong style="color:blue;">integerField</strong>;
 *		
 *  <i>@</i>TLabel(text="Big number field:", control=<i>@</i>TControl(prefWidth=500))
 *  <i>@</i>TBigIntegerField(zeroValidation=TZeroValidation.MORE_THAN_ZERO, control=<i>@</i>TControl(tooltip="Max val: infinito"))
 *  private SimpleObjectProperty&gt;BigInteger&lt; <strong style="color:green;">bigIntegerField</strong>;
 * 
 * 
 * Oracle documentation:
 * 
 * BorderPane lays out children in top, left, 
 * right, bottom, and center positions.
 * 
 * The top and bottom children will be resized 
 * to their preferred heights and extend the width 
 * of the border pane. The left and right children
 * will be resized to their preferred widths and 
 * extend the length between the top and bottom nodes. 
 * And the center node will be resized to fill the 
 * available space in the middle. 
 * Any of the positions may be null.
 * </pre>
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE,ElementType.FIELD})
public @interface TBorderPane {
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.ANNOTATION_TYPE})
	public @interface TAlignment {
		
		@SuppressWarnings("rawtypes")
		public Class<? extends ITAnnotationParser>[] parser() default {TBorderPaneAlignmentParser.class};
		
		public TPos[] values() default {} ; 
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.ANNOTATION_TYPE})
	public @interface TMargin {
		
		@SuppressWarnings("rawtypes")
		public Class<? extends ITAnnotationParser>[] parser() default {TBorderPaneMarginParser.class};
		
		public TFieldInset[] values() default {}; 
	}
	
	/**
	 *<pre>
	 * The builder of type {@link ITLayoutBuilder} for this component.
	 * 
	 *  Default value: {@link TBorderPaneBuilder}
	 *</pre> 
	 * */
	public Class<? extends ITLayoutBuilder<BorderPane>> builder() default TBorderPaneBuilder.class;
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TBorderPaneParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {TBorderPaneParser.class};
	
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
	* {@link BorderPane} Class
	* 
	*  Sets the alignment for the child when contained by a border pane. If set, will override the border pane's default alignment for the child's position. Setting the value to null will remove the constraint. Parameters: child - the child node of a border pane value - the alignment position for the child
	* </pre>
	**/
	public TAlignment alignment() default @TAlignment();

	/**
	* <pre>
	* {@link BorderPane} Class
	* 
	*  Sets the margin for the child when contained by a border pane. If set, the border pane will lay it out with the margin space around it. Setting the value to null will remove the constraint. Parameters: child - the child node of a border pane value - the margin of space around the child
	* </pre>
	**/
	public TMargin margin() default @TMargin();

	/**
	* <pre>
	* {@link BorderPane} Class
	* 
	*  Sets the value of the property center. 
	*  
	*  Property description: 
	*  The node placed in the center of this border pane. 
	*  If resizable, it will be resized fill the center of 
	*  the border pane between the top, bottom, left, and right nodes. 
	*  If the node cannot be resized to fill the center space 
	*  (it's not resizable or its max size prevents it) then it will be 
	*  center aligned unless the child's alignment constraint has been set.
	* </pre>
	**/
	public String center() default "";

	/**
	* <pre>
	* {@link BorderPane} Class
	* 
	*  Scroll the content of the fields.
	* </pre>
	**/
	public String[] scroll() default {};

	/**
	* <pre>
	* {@link BorderPane} Class
	* 
	*  Sets the value of the property top. 
	*  
	*  Property description: 
	*  The node placed on the top edge of this border pane. 
	*  If resizable, it will be resized to its preferred height 
	*  and it's width will span the width of the border pane. 
	*  If the node cannot be resized to fill the top space 
	*  (it's not resizable or its max size prevents it) then it 
	*  will be aligned top-left within the space unless the child's 
	*  alignment constraint has been set.
	* </pre>
	**/
	public String top() default "";

	/**
	* <pre>
	* {@link BorderPane} Class
	* 
	*  Sets the value of the property bottom. 
	*  
	*  Property description: 
	*  The node placed on the bottom edge of this border pane. 
	*  If resizable, it will be resized to its preferred height 
	*  and it's width will span the width of the border pane. 
	*  If the node cannot be resized to fill the bottom space 
	*  (it's not resizable or its max size prevents it) then it 
	*  will be aligned bottom-left within the space unless the child's 
	*  alignment constraint has been set.
	* </pre>
	**/
	public String bottom() default "";

	/**
	* <pre>
	* {@link BorderPane} Class
	* 
	*  Sets the value of the property left. 
	*  
	*  Property description: 
	*  The node placed on the left edge of this border pane. 
	*  If resizable, it will be resized to its preferred width 
	*  and it's height will span the height of the border pane 
	*  between the top and bottom nodes. 
	*  If the node cannot be resized to fill the left space 
	*  (it's not resizable or its max size prevents it) then it 
	*  will be aligned top-left within the space unless the child's 
	*  alignment constraint has been set.
	* </pre>
	**/
	public String left() default "";

	/**
	* <pre>
	* {@link BorderPane} Class
	* 
	*  Sets the value of the property right. 
	*  
	*  Property description: 
	*  The node placed on the right edge of this border pane. 
	*  If resizable, it will be resized to its preferred width 
	*  and it's height will span the height of the border pane 
	*  between the top and bottom nodes. 
	*  If the node cannot be resized to fill the right space 
	*  (it's not resizable or its max size prevents it) then 
	*  it will be aligned top-right within the space unless the 
	*  child's alignment constraint has been set.
	* </pre>
	**/
	public String right() default "";
	
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
