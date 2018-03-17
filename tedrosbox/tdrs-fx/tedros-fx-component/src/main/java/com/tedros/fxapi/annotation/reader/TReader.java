/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 09/03/2014
 */
package com.tedros.fxapi.annotation.reader;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.shape.Shape;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;

import com.tedros.core.model.ITModelView;
import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.fxapi.annotation.TBooleanValues;
import com.tedros.fxapi.annotation.control.TConverter;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.parser.TAnnotationParser;
import com.tedros.fxapi.annotation.parser.TDateTimeReaderParser;
import com.tedros.fxapi.annotation.parser.TTextParser;
import com.tedros.fxapi.annotation.parser.TTextReaderParser;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.shape.TShape;
import com.tedros.fxapi.annotation.text.TFont;
import com.tedros.fxapi.builder.ITReaderBuilder;
import com.tedros.fxapi.builder.TReaderBuilder;
import com.tedros.fxapi.reader.TTextReader;

/**
 * <pre>
 * Mark the field as a Reader type.
 * 
 * A reader type field is builded only when the user set the view to reader mode.
 * 
 * Types supported:
 * 
 * {@link SimpleStringProperty}, {@link SimpleBooleanProperty}, {@link SimpleLongProperty}, 
 * {@link SimpleDoubleProperty}, {@link SimpleFloatProperty}, {@link SimpleIntegerProperty}, 
 *  and {@link SimpleObjectProperty} 
 * 
 * Note: 
 * 
 * A field of {@link ITEntity} type in an entity class can be configured as 
 * SimpleObjectProperty&lt;ITEntity&gt; at the {@link ITModelView} representation. 
 * 
 * </pre>
 *
 * @see TReaderDefaultSetting
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TReader {
	
	/**
	 *<pre>
	 * The builder of type {@link ITReaderBuilder} for this component.
	 * 
	 *  Default value: {TReaderBuilder.class}
	 *</pre> 
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITReaderBuilder> builder() default TReaderBuilder.class;
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TTextReaderParser.class, TDateTimeReaderParser.class, TTextParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TAnnotationParser>[] parser() default {TTextReaderParser.class, TDateTimeReaderParser.class, TTextParser.class};
	
	/**
	 * <pre>
	 * The {@link Node} settings for this component.
	 * </pre>
	 * */
	public TNode node() default @TNode(parse=false);
	
	/**
	 * <pre>
	 * The {@link Shape} settings for this node.
	 * </pre>
	 * */
	public TShape shape() default @TShape(parse = false);
	
	/**
	* <pre>
	* {@link Text} Class
	* 
	*  Sets the value of the property text. 
	*  
	*  Property description: 
	*  
	*  Defines text string that is to be displayed. 
	*  
	*  Default value: empty string
	* </pre>
	**/
	public String text() default "";

	/**
	* <pre>
	* {@link Text} Class
	* 
	*  Sets the value of the property x. 
	*  
	*  Property description: 
	*  
	*  Defines the X coordinate of text origin. 
	*  
	*  Default value: 0
	* </pre>
	**/
	public double x() default 0;

	/**
	* <pre>
	* {@link Text} Class
	* 
	*  Sets the value of the property y. 
	*  
	*  Property description: 
	*  
	*  Defines the Y coordinate of text origin. 
	*  
	*  Default value: 0
	* </pre>
	**/
	public double y() default 0;

	/**
	* <pre>
	* {@link Text} Class
	* 
	*  Sets the value of the property font. 
	*  
	*  Property description: 
	*  
	*  Defines the font of text. 
	*  
	* </pre>
	**/
	public TFont font() default @TFont();

	/**
	* <pre>
	* {@link Text} Class
	* 
	*  Sets the value of the property textOrigin. 
	*  
	*  Property description: 
	*  
	*  Defines the origin of text coordinate system in local coordinates. 
	*  
	*  Note: in case multiple rows are rendered VPos.BASELINE and VPos.TOP define 
	*  the origin of the top row while VPos.BOTTOM defines the origin of the bottom row. 
	*  
	*  Default value: VPos.BASELINE
	* </pre>
	**/
	public VPos textOrigin() default VPos.BASELINE;

	/**
	* <pre>
	* {@link Text} Class
	* 
	*  Sets the value of the property boundsType. 
	*  
	*  Property description: 
	*  
	*  Determines how the bounds of the text node are calculated. 
	*  Logical bounds is a more appropriate default for text than 
	*  the visual bounds. See TextBoundsType for more information. 
	*  
	*  Default value: TextBoundsType.LOGICAL
	* </pre>
	**/
	public TextBoundsType boundsType() default TextBoundsType.LOGICAL;

	/**
	* <pre>
	* {@link Text} Class
	* 
	*  Sets the value of the property wrappingWidth. 
	*  
	*  Property description: 
	*  
	*  Defines a width constraint for the text in user space coordinates,
	*   e.g. pixels, not glyph or character count. If the value is > 0 text 
	*   will be line wrapped as needed to satisfy this constraint. 
	*   
	*   Default value: 0
	* </pre>
	**/
	public double wrappingWidth() default 0;

	/**
	* <pre>
	* {@link Text} Class
	* 
	*  Sets the value of the property underline. 
	*  
	*  Property description: 
	*  
	*  Defines if each line of text should have a line below it. 
	*  
	*  Default value: false
	* </pre>
	**/
	public boolean underline() default false;

	/**
	* <pre>
	* {@link Text} Class
	* 
	*  Sets the value of the property strikethrough. 
	*  
	*  Property description: 
	*  
	*  Defines if each line of text should have a line through it. 
	*  
	*  Default value: false
	* </pre>
	**/
	public boolean strikethrough() default false;

	/**
	* <pre>
	* {@link Text} Class
	* 
	*  Sets the value of the property textAlignment. 
	*  
	*  Property description: 
	*  
	*  Defines horizontal text alignment in the bounding box. 
	*  The width of the bounding box is defined by the widest row. 
	*  Note: In the case of a single line of text, where the width of 
	*  the node is determined by the width of the text, the alignment 
	*  setting has no effect. 
	*  
	*  Default value: TextAlignment.LEFT
	* </pre>
	**/
	public TextAlignment textAlignment() default TextAlignment.LEFT;

	/**
	* <pre>
	* {@link Text} Class
	* 
	*  Sets the value of the property fontSmoothingType. 
	*  
	*  Property description: 
	*  
	*  Specifies a requested font smoothing type : gray or LCD. 
	*  The width of the bounding box is defined by the widest row. 
	*  Note: LCD mode doesn't apply in numerous cases, such as various 
	*  compositing modes, where effects are applied and very large glyphs. 
	*  
	*  Default value: FontSmoothingType.GRAY
	* </pre>
	**/
	public FontSmoothingType fontSmoothingType() default FontSmoothingType.GRAY;


	/**
	* <pre>
	* {@link TTextReader} Class
	* 
	*  Sets the value of the property showActionsToolTip. 
	*  
	*  Property description: 
	*  
	*  Show an action tool tip with possible actions when the mouse entered above this component.  
	*  
	*  Default value: false
	* </pre>
	**/
	public boolean showActionsToolTip() default false;
	
	/**
	* <pre>
	* {@link TTextReader} Class
	* 
	*  Sets the value of the property mask. 
	*  
	*  Property description: 
	*  
	*  Specifies a mask for value.  
	*  
	*  Default value: empty string
	* </pre>
	**/
	public String mask() default "";
	
	/**
	* <pre>
	* {@link TTextReader} Class
	* 
	*  Sets the value of the property datePattern. 
	*  
	*  Property description: 
	*  
	*  Specifies a date pattern for Date fields.  
	*  
	*  Default value: dd/MM/yyyy
	* </pre>
	**/
	public String datePattern() default "dd/MM/yyyy";
	
	/**
	* <pre>
	*  Suppress the field label and change it for the reader label.   
	*  
	*  Default value: false
	* </pre>
	**/
	public boolean suppressLabels() default false;
	
	/**
	 * <pre>
	 * Sets the value of the property booleanValues. 
	 *  
	 *  Property description: 
	 *  
	 *  Specifies a description for boolean fields.  
	 *  
	 *  Default value: @TBooleanValues()
	 * </pre> 
	 * */
	public TBooleanValues booleanValues() default @TBooleanValues();
	
	/**
	 * <pre>
	 *  The label for the reader field
	 * </pre> 
	 * */
	public TLabel label() default @TLabel(text="");
	
	/**
	 * Specifies a converter class. 
	 * */
	public TConverter converter() default @TConverter(type=com.tedros.fxapi.form.TConverter.class, parse = false);
	
	
}
