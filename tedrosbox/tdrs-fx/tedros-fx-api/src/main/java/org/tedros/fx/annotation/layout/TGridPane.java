package org.tedros.fx.annotation.layout;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.api.parser.ITAnnotationParser;
import org.tedros.api.presenter.view.TViewMode;
import org.tedros.fx.annotation.parser.TGridPaneHAlignParser;
import org.tedros.fx.annotation.parser.TGridPaneHGrowParser;
import org.tedros.fx.annotation.parser.TGridPaneMarginParser;
import org.tedros.fx.annotation.parser.TGridPaneParser;
import org.tedros.fx.annotation.parser.TGridPaneVAlignParser;
import org.tedros.fx.annotation.parser.TGridPaneVGrowParser;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.builder.ITLayoutBuilder;
import org.tedros.fx.builder.TGridPaneBuilder;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;


/**
 * <pre>
 * Build a {@link GridPane} layout.
 * 
 * Example:
 * 
 * <i>@</i><strong>TGridPane</strong>(add={<i>@</i>TField(name = "passField", columnIndex = 0,  rowIndex = 0, columnspan=2),
 * <i>@</i>TField(name = "integerField", columnIndex = 0,  rowIndex = 1),
 * <i>@</i>TField(name = "bigIntegerField", columnIndex = 1,  rowIndex = 1)
 * 	})
 *  <i>@</i>TLabel(text="Password:", control=<i>@</i>TControl(prefWidth=500))
 *  <i>@</i>TPasswordField(required=true, maxLength=6)
 *  private SimpleStringProperty passField;
 *		
 *  <i>@</i>TLabel(text="Number field", control=<i>@</i>TControl(prefWidth=500))
 *  <i>@</i>TIntegerField(validate=TValidateNumber.GREATHER_THAN_ZERO, control=<i>@</i>TControl(tooltip="Max val: "+Integer.MAX_VALUE))
 *  private SimpleIntegerProperty integerField;
 *		
 *  <i>@</i>TLabel(text="Big number field:", control=<i>@</i>TControl(prefWidth=500))
 *  <i>@</i>TBigIntegerField(validate=TValidateNumber.GREATHER_THAN_ZERO, control=<i>@</i>TControl(tooltip="Max val: infinito"))
 *  private SimpleObjectProperty&gt;BigInteger&lt; bigIntegerField;
 * 
 * 
 * Oracle documentation:
 * 
 * GridPane lays out its children within 
 * a flexible grid of rows and columns. 
 * If a border and/or padding is set, then 
 * its content will be layed out within those insets.
 * 
 * A child may be placed anywhere within 
 * the grid and may span multiple rows/columns. 
 * Children may freely overlap within rows/columns 
 * and their stacking order will be defined by 
 * the order of the gridpane's children list 
 * (0th node in back, last node in front).
 * </pre>
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE,ElementType.FIELD})
public @interface TGridPane {
	
	/**
	 * Indicate the field and the column/row to be added 
	 * */
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.ANNOTATION_TYPE})
	public @interface TField {
		public String name();
		public int columnIndex();
		public int rowIndex();
		public int columnspan() default 1;
		public int rowspan() default 1;
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.ANNOTATION_TYPE})
	public @interface THGrow {
		
		@SuppressWarnings("rawtypes")
		public Class<? extends ITAnnotationParser>[] parser() default {TGridPaneHGrowParser.class};
		
		public TPriority[] priority() default {} ; 
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.ANNOTATION_TYPE})
	public @interface TVGrow {
		
		@SuppressWarnings("rawtypes")
		public Class<? extends ITAnnotationParser>[] parser() default {TGridPaneVGrowParser.class};
		
		public TPriority[] priority() default {} ; 
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.ANNOTATION_TYPE})
	public @interface THAlignment {
		
		@SuppressWarnings("rawtypes")
		public Class<? extends ITAnnotationParser>[] parser() default {TGridPaneHAlignParser.class};
		
		public THPos[] values() default {} ; 
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.ANNOTATION_TYPE})
	public @interface TVAlignment {
		
		@SuppressWarnings("rawtypes")
		public Class<? extends ITAnnotationParser>[] parser() default {TGridPaneVAlignParser.class};
		
		public TVPos[] values() default {} ; 
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.ANNOTATION_TYPE})
	public @interface TMargin {
		
		@SuppressWarnings("rawtypes")
		public Class<? extends ITAnnotationParser>[] parser() default {TGridPaneMarginParser.class};
		
		public TFieldInset[] values() default {};
	}
	
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.ANNOTATION_TYPE})
	public @interface TFieldBoolean {
		public String field(); 
		public boolean value();
	}
	
	/**
	 *<pre>
	 * The builder of type {@link ITLayoutBuilder} for this component.
	 * 
	 *  Default value: {@link TGridPaneBuilder}
	 *</pre> 
	 * */
	public Class<? extends ITLayoutBuilder<GridPane>> builder() default TGridPaneBuilder.class;
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TBorderPaneParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {TGridPaneParser.class};
	
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
	* {@link GridPane} Class
	* 
	*  Sets the margin for the child when contained 
	*  by a gridpane. If set, the gridpane will lay it 
	*  out with the margin space around it. 
	*  Setting the value to null will remove the constraint.
	* </pre>
	**/
	public TMargin margin() default @TMargin();

	/**
	* <pre>
	* {@link GridPane} Class
	* 
	*  Sets the horizontal alignment for the child 
	*  when contained by a gridpane. 
	*  If set, will override the gridpane's default 
	*  horizontal alignment. Setting the value to null 
	*  will remove the constraint. 
	* </pre>
	**/
	public THAlignment halignment() default @THAlignment();

	/**
	* <pre>
	* {@link GridPane} Class
	* 
	*  Sets the vertical alignment for the child 
	*  when contained by a gridpane. 
	*  If set, will override the gridpane's default 
	*  vertical alignment. Setting the value to null will 
	*  remove the constraint. 
	* </pre>
	**/
	public TVAlignment valignment() default @TVAlignment();

	/**
	* <pre>
	* {@link GridPane} Class
	* 
	*  Sets the horizontal grow priority for the child 
	*  when contained by a gridpane. 
	*  If set, the gridpane will use the priority to allocate 
	*  the child additional horizontal space if the gridpane 
	*  is resized larger than it's preferred width. 
	*  Setting the value to null will remove the constraint. 
	* </pre>
	**/
	public THGrow hgrow() default @THGrow;

	/**
	* <pre>
	* {@link GridPane} Class
	* 
	*  Sets the vertical grow priority for the child 
	*  when contained by a gridpane. 
	*  If set, the gridpane will use the priority to 
	*  allocate the child additional vertical space if 
	*  the gridpane is resized larger than it's preferred 
	*  height. Setting the value to null will remove the
	*  constraint. 
	* </pre>
	**/
	public TVGrow vgrow() default @TVGrow;

	/**
	* <pre>
	* {@link GridPane} Class
	* 
	*  Sets the horizontal fill policy for the child 
	*  when contained by a gridpane. If set, the gridpane 
	*  will use the policy to determine whether node should 
	*  be expanded to fill the column or kept to it's 
	*  preferred width. Setting the value to null will remove 
	*  the constraint. If not value is specified for the 
	*  node nor for the column, the default value is true. 
	*  Parameters: field - the child node of a gridpane
	*   value - the horizontal fill policy 
	*   Since: JavaFX 8.0
	* </pre>
	**/
	public TFieldBoolean[] fillWidth() default {};

	/**
	* <pre>
	* {@link GridPane} Class
	* 
	*  Sets the vertical fill policy for the child when 
	*  contained by a gridpane. If set, the gridpane 
	*  will use the policy to determine whether node should 
	*  be expanded to fill the row or kept to it's preferred 
	*  height. Setting the value to null will remove the
	*  constraint. If not value is specified for the node 
	*  nor for the row, the default value is true.
	*  Parameters: field - the child node of a gridpane 
	*  value - the vertical fill policy
	*  Since: JavaFX 8.0
	* </pre>
	**/
	public TFieldBoolean[]  fillHeight() default {};

	/**
	* <pre>
	* {@link GridPane} Class
	* 
	*  Adds a child to the gridpane at the specified 
	*  column,row position and spans.
	* </pre>
	**/
	public TField[] add() default {};

	/**
	* <pre>
	* {@link GridPane} Class
	* 
	*  Sets the value of the property hgap. 
	*  
	*  Property description: 
	*  The width of the horizontal gaps between columns.
	*  
	*  Default value: 8
	* </pre>
	**/
	public double hgap() default 8;

	/**
	* <pre>
	* {@link GridPane} Class
	* 
	*  Sets the value of the property vgap. 
	*  
	*  Property description: 
	*  The height of the vertical gaps between rows.
	*  
	*  Default value: 8
	* </pre>
	**/
	public double vgap() default 8;

	/**
	* <pre>
	* {@link GridPane} Class
	* 
	*  Sets the value of the property alignment. 
	*  
	*  Property description: 
	*  The alignment of of the grid within the gridpane's width and height.
	*  
	*  Default value: Pos.CENTER
	* </pre>
	**/
	public Pos alignment() default Pos.CENTER;

	/**
	* <pre>
	* {@link GridPane} Class
	* 
	*  Sets the value of the property gridLinesVisible. 
	*  
	*  Property description:
	*  For debug purposes only: controls whether lines are displayed 
	*  to show the gridpane's rows and columns. Default is false.
	* </pre>
	**/
	public boolean gridLinesVisible() default false;

	
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
