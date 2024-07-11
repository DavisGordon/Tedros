package org.tedros.fx.annotation.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.api.parser.ITAnnotationParser;
import org.tedros.fx.annotation.TDefaultValue;
import org.tedros.fx.annotation.parser.TScrollPaneParser;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.builder.ITLayoutBuilder;
import org.tedros.fx.builder.TScrollPaneBuilder;
import org.tedros.fx.domain.TDefaultValues;

import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.VBox;


/**
 * <pre>
 * Build a {@link VBox} layout.
 * 
 * Example:
 * 
 * <i>@</i><strong>TVBox</strong>(pane=<i>@</i>TPane(children={"<strong style="color:red;">passField"</strong>, "<strong style="color:blue;">integerField</strong>", "<strong style="color:green;">bigIntegerField</strong>"}), fillHeight=true, 
 *			vgrow=<i>@</i>TVGrow(priority=<i>@</i>TPriority(field="bigIntegerField", priority=Priority.ALWAYS)),
 *			spacing=10)
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
 *  private SimpleObjectProperty&gt;BigInteger&lt; <strong style="color:green;">bigIntegerField</strong>;
 * 
 * 
 * Oracle documentation:
 * 
 * VBox lays out its children in a single vertical column. If the vbox has a border and/or padding set, then the contents will be layed out within those insets.
 *
 * VBox will resize children (if resizable) to their preferred heights and uses its fillWidth property to determine whether to resize their widths to fill its own width or keep their widths to their preferred (fillWidth defaults to true). The alignment of the content is controlled by the alignment property, which defaults to Pos.TOP_LEFT.
 * If a vbox is resized larger than its preferred height, by default it will keep children to their preferred heights, leaving the extra space unused. If an application wishes to have one or more children be allocated that extra space it may optionally set a vgrow constraint on the child. See "Optional Layout Constraints" for details.
 * 
 * VBox lays out each managed child regardless of the child's visible property value; unmanaged children are ignored.
 * 
 * </pre>
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE,ElementType.FIELD})
public @interface TScrollPane {
	
	/**
	 *<pre>
	 * The builder of type {@link ITLayoutBuilder} for this component.
	 * 
	 *  Default value: {@link TScrollPaneBuilder}
	 *</pre> 
	 * */
	public Class<? extends ITLayoutBuilder<ScrollPane>> builder() default TScrollPaneBuilder.class;
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TScrollPaneParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {TScrollPaneParser.class};
	
	/**
	 * <pre>
	 * The {@link Node} settings.
	 * </pre>
	 * */
	public TNode node() default @TNode(parse = false);
	
	/**
	 * <pre>
	 * The {@link Control} settings.
	 * 
	 * Default value: @TControl(prefWidth=250) 
	 * </pre>
	 * */
	public TControl control() default @TControl(prefWidth=TDefaultValues.LABEL_WIDTH, parse = true);

	/**
	* <pre>
	* {@link ScrollPane} Class
	* 
	*  Sets the value of the property content. 
	*  
	*  Property description: 
	*  The node used as the content of this ScrollPane.
	* </pre>
	**/
	public String content();
	
	/**
	* <pre>
	* {@link ScrollPane} Class
	* 
	*  Sets the value of the property hbarPolicy. Property description: Specifies the policy for showing the horizontal scroll bar.
	* </pre>
	**/
	public ScrollBarPolicy hbarPolicy() default ScrollBarPolicy.AS_NEEDED;

	/**
	* <pre>
	* {@link ScrollPane} Class
	* 
	*  Sets the value of the property vbarPolicy. Property description: Specifies the policy for showing the vertical scroll bar.
	* </pre>
	**/
	public ScrollBarPolicy vbarPolicy() default ScrollBarPolicy.AS_NEEDED;

	/**
	* <pre>
	* {@link ScrollPane} Class
	* 
	*  Sets the value of the property hvalue. 
	*  
	*  Property description: 
	*  The current horizontal scroll position of the ScrollPane. 
	*  This value may be set by the application to scroll the view programatically. 
	*  The ScrollPane will update this value whenever the viewport is scrolled 
	*  or panned by the user. This value must always be within the range 
	*  of hmin to hmax. When hvalue equals hmin, the contained node is 
	*  positioned so that its layoutBounds minX is visible. 
	*  When hvalue equals hmax, the contained node is positioned so that its 
	*  layoutBounds maxX is visible. When hvalue is between hmin and hmax, 
	*  the contained node is positioned proportionally between layoutBounds 
	*  minX and layoutBounds maxX.
	* </pre>
	**/
	public double hvalue() default TDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION;

	/**
	* <pre>
	* {@link ScrollPane} Class
	* 
	*  Sets the value of the property vvalue. 
	*  
	*  Property description: 
	*  The current vertical scroll position of the ScrollPane. 
	*  This value may be set by the application to scroll the view programatically. 
	*  The ScrollPane will update this value whenever the viewport is scrolled or 
	*  panned by the user. This value must always be within the range of vmin to vmax. 
	*  When vvalue equals vmin, the contained node is positioned so that its layoutBounds 
	*  minY is visible. When vvalue equals vmax, the contained node is positioned so 
	*  that its layoutBounds maxY is visible. When vvalue is between vmin and vmax, 
	*  the contained node is positioned proportionally between layoutBounds minY and 
	*  layoutBounds maxY.
	* </pre>
	**/
	public double vvalue() default TDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION;

	/**
	* <pre>
	* {@link ScrollPane} Class
	* 
	*  Sets the value of the property hmin. 
	*  
	*  Property description: 
	*  The minimum allowable hvalue for this ScrollPane.
	* </pre>
	**/
	public double hmin() default TDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION;

	/**
	* <pre>
	* {@link ScrollPane} Class
	* 
	*  Sets the value of the property vmin. 
	*  
	*  Property description: 
	*  The minimum allowable vvalue for this ScrollPane.
	* </pre>
	**/
	public double vmin() default TDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION;

	/**
	* <pre>
	* {@link ScrollPane} Class
	* 
	*  Sets the value of the property hmax. 
	*  
	*  Property description: 
	*  The maximum allowable hvalue for this ScrollPane.
	* </pre>
	**/
	public double hmax() default TDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION;

	/**
	* <pre>
	* {@link ScrollPane} Class
	* 
	*  Sets the value of the property vmax. 
	*  
	*  Property description: 
	*  The maximum allowable vvalue for this ScrollPane.
	* </pre>
	**/
	public double vmax() default TDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION;

	/**
	* <pre>
	* {@link ScrollPane} Class
	* 
	*  Sets the value of the property fitToWidth. 
	*  
	*  Property description: 
	*  If true and if the contained node is a Resizable, 
	*  then the node will be kept resized to match the width 
	*  of the ScrollPane's viewport. If the contained node is 
	*  not a Resizable, this value is ignored.
	* </pre>
	**/
	public boolean fitToWidth() default true;

	/**
	* <pre>
	* {@link ScrollPane} Class
	* 
	*  Sets the value of the property fitToHeight. 
	*  
	*  Property description: 
	*  If true and if the contained node is a Resizable, 
	*  then the node will be kept resized to match the height 
	*  of the ScrollPane's viewport. If the contained node is 
	*  not a Resizable, this value is ignored.
	* </pre>
	**/
	public boolean fitToHeight() default true;

	/**
	* <pre>
	* {@link ScrollPane} Class
	* 
	*  Sets the value of the property pannable. 
	*  
	*  Property description: 
	*  Specifies whether the user should be able to pan the 
	*  viewport by using the mouse. If mouse events reach the 
	*  ScrollPane (that is, if mouse events are not blocked by 
	*  the contained node or one of its children) then pannable 
	*  is consulted to determine if the events should be used 
	*  for panning.
	* </pre>
	**/
	public boolean pannable() default false;

	/**
	* <pre>
	* {@link ScrollPane} Class
	* 
	*  Sets the value of the property prefViewportWidth. 
	*  
	*  Property description: Specify the perferred width of 
	*  the ScrollPane Viewport. This is the width that will be 
	*  available to the content node. The overall width of the 
	*  ScrollPane is the ViewportWidth + padding
	* </pre>
	**/
	public double prefViewportWidth() default TDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION;

	/**
	* <pre>
	* {@link ScrollPane} Class
	* 
	*  Sets the value of the property prefViewportHeight. 
	*  
	*  Property description: 
	*  Specify the preferred height of the ScrollPane Viewport. 
	*  This is the height that will be available to the content node. 
	*  The overall height of the ScrollPane is the ViewportHeight + padding
	* </pre>
	**/
	public double prefViewportHeight() default TDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION;
	
}
