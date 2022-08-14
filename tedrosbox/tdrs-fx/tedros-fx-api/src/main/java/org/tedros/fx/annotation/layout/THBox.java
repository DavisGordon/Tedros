package org.tedros.fx.annotation.layout;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.fx.annotation.TAnnotationDefaultValue;
import org.tedros.fx.annotation.parser.ITAnnotationParser;
import org.tedros.fx.annotation.parser.THBoxParser;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.builder.ITLayoutBuilder;
import org.tedros.fx.builder.THBoxBuilder;
import org.tedros.fx.domain.TViewMode;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;


/**
 * <pre>
 * 
 * THBox build a {@link HBox} layout.
 * 
 * Example:
 * 
 * <i>@</i><strong>THBox</strong>(pane=<i>@</i>TPane(children={"<strong style="color:red;">passField"</strong>, "<strong style="color:blue;">integerField</strong>", "<strong style="color:green;">bigIntegerField</strong>"}), fillHeight=true, 
 *			hgrow=<i>@</i>THGrow(priority=<i>@</i>TPriority(field="bigIntegerField", priority=Priority.ALWAYS)),
 *			spacing=10)
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
 *  private SimpleObjectProperty&lt;BigInteger&gt; <strong style="color:green;">bigIntegerField</strong>;
 * 
 * Oracle documentation:
 *  
 * HBox lays out its children in a single horizontal row. If the hbox has a border and/or padding set, 
 * then the contents will be layed out within those insets.
 * 
 * HBox will resize children (if resizable) to their preferred widths and uses its fillHeight property 
 * to determine whether to resize their heights to fill its own height or keep their heights to their 
 * preferred (fillHeight defaults to true). The alignment of the content is controlled by the alignment 
 * property, which defaults to Pos.TOP_LEFT.
 * 
 * If an hbox is resized larger than its preferred width, by default it will keep children to their 
 * preferred widths, leaving the extra space unused. If an application wishes to have one or more children 
 * be allocated that extra space it may optionally set an hgrow constraint on the child. See "Optional 
 * Layout Constraints" for details.
 *
 * HBox lays out each managed child regardless of the child's visible property value; unmanaged children 
 * are ignored.
 * </pre>
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE,ElementType.FIELD})
public @interface THBox {
	
	/**
	 *<pre>
	 * The builder of type {@link ITLayoutBuilder} for this component.
	 * 
	 *  Default value: {@link THBoxBuilder}
	 *</pre> 
	 * */
	public Class<? extends ITLayoutBuilder<HBox>> builder() default THBoxBuilder.class;
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {THBoxParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {THBoxParser.class};
	
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
	public TPane pane() default @TPane;

	/**
	* <pre>
	* {@link HBox} Class
	* 
	*  Sets the margin for the child when contained by an hbox. If set, the hbox will layout the child with the margin space around it. Setting the value to null will remove the constraint. Parameters: child - the child mode of the hbox value - the margin of space around the child
	* </pre>
	**/
	public TMargin margin() default @TMargin;

	/**
	* <pre>
	* {@link HBox} Class
	* 
	*  Sets the value of the property spacing. Property description: The amount of horizontal space between each child in the hbox.
	* </pre>
	**/
	public double spacing() default TAnnotationDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION;

	/**
	* <pre>
	* {@link HBox} Class
	* 
	*  Sets the value of the property alignment. Property description: The overall alignment of children within the hbox's width and height. If the vertical alignment value is BASELINE, then children will always be resized to their preferred heights and the fillHeight property will be ignored.
	* </pre>
	**/
	public Pos alignment() default Pos.CENTER;

	/**
	* <pre>
	* {@link HBox} Class
	* 
	*  Sets the value of the property fillHeight. Property description: Whether or not resizable children will be resized to fill the full height of the hbox or be kept to their preferred height and aligned according to the alignment vpos value. Note that if the hbox vertical alignment is set to BASELINE, then this property will be ignored and children will be resized to their preferred heights.
	* </pre>
	**/
	public boolean fillHeight() default false ;
	
	/**
	* <pre>
	* {@link HBox} Class
	* 
	*  Sets the horizontal grow priority for the child when contained by an hbox. If set, the hbox will use the priority to allocate additional space if the hbox is resized larger than it's preferred width. If multiple hbox children have the same horizontal grow priority, then the extra space will be split evening between them. If no horizontal grow priority is set on a child, the hbox will never allocate it additional horizontal space if available. Setting the value to null will remove the constraint. Parameters: child - the child of an hbox value - the horizontal grow priority for the child
	* </pre>
	**/
	public THGrow hgrow() default @THGrow;
	
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
