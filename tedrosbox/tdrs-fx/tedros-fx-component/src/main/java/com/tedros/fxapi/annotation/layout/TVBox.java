package com.tedros.fxapi.annotation.layout;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import com.tedros.fxapi.annotation.TAnnotationDefaultValue;
import com.tedros.fxapi.annotation.parser.ITAnnotationParser;
import com.tedros.fxapi.annotation.parser.TVBoxParser;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.layout.TRegion;
import com.tedros.fxapi.builder.ITLayoutBuilder;
import com.tedros.fxapi.builder.TVBoxBuilder;
import com.tedros.fxapi.domain.TViewMode;


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
public @interface TVBox {
	
	/**
	 *<pre>
	 * The builder of type {@link ITLayoutBuilder} for this component.
	 * 
	 *  Default value: {@link TVBoxBuilder}
	 *</pre> 
	 * */
	public Class<? extends ITLayoutBuilder<VBox>> builder() default TVBoxBuilder.class;
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TRequiredNumeberFieldParser.class, TNumberFieldParser.class, TTextFieldParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {TVBoxParser.class};
	
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
	* {@link VBox} Class
	* 
	*  Sets the vertical grow priority for the child when contained by an vbox. If set, the vbox will use the priority to allocate additional space if the vbox is resized larger than it's preferred height. If multiple vbox children have the same vertical grow priority, then the extra space will be split evenly between them. If no vertical grow priority is set on a child, the vbox will never allocate it additional vertical space if available. Setting the value to null will remove the constraint. Parameters: child - the child of a vbox value - the horizontal grow priority for the child
	* </pre>
	**/
	public TVGrow vgrow() default @TVGrow;

	/**
	* <pre>
	* {@link VBox} Class
	* 
	*  Sets the margin for the child when contained by a vbox. If set, the vbox will layout the child so that it has the margin space around it. Setting the value to null will remove the constraint. Parameters: child - the child mode of a vbox value - the margin of space around the child
	* </pre>
	**/
	public TVBoxMargin margin() default @TVBoxMargin;

	/**
	* <pre>
	* {@link VBox} Class
	* 
	*  Sets the value of the property spacing. Property description: The amount of vertical space between each child in the vbox.
	* </pre>
	**/
	public double spacing() default TAnnotationDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION;

	/**
	* <pre>
	* {@link VBox} Class
	* 
	*  Sets the value of the property alignment. Property description: The overall alignment of children within the vbox's width and height.
	* </pre>
	**/
	public Pos alignment() default Pos.CENTER;

	/**
	* <pre>
	* {@link VBox} Class
	* 
	*  Sets the value of the property fillWidth. Property description: Whether or not resizable children will be resized to fill the full width of the vbox or be kept to their preferred width and aligned according to the alignment hpos value.
	* </pre>
	**/
	public boolean fillWidth() default false;
	
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
