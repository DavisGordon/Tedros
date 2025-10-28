/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 09/03/2014
 */
package org.tedros.fx.annotation.reader;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.core.model.ITModelView;
import org.tedros.fx.annotation.control.TLabelDefaultSetting;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.shape.TShape;
import org.tedros.fx.annotation.text.TFont;
import org.tedros.fx.reader.TTextReader;

import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.shape.Shape;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;

/**
 * <pre>
 * Sets the default values for all {@link TReader} present in the {@link ITModelView}.
 * 
 * But the preference always be for the {@link TReader} values if they are setted.
 * </pre>
 *
 * @see TReader
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TReaderDefaultSetting {
	
	
	/**
	 * Specifies the default settings for all labels in the @{@link TReader}(label="")
	 * */
	public TLabelDefaultSetting labelDefaultSettings() default @TLabelDefaultSetting();
	
	/**
	 * <pre>
	 * The {@link Node} settings for this component.
	 * </pre>
	 * */
	public TNode node() default @TNode(parse = false);
	
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
	
}
