package org.tedros.fx.annotation.text;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.api.parser.ITAnnotationParser;
import org.tedros.api.presenter.view.TViewMode;
import org.tedros.fx.annotation.control.TConverter;
import org.tedros.fx.annotation.parser.TTextParser;
import org.tedros.fx.annotation.property.TBooleanProperty;
import org.tedros.fx.annotation.property.TDoubleProperty;
import org.tedros.fx.annotation.property.TObjectProperty;
import org.tedros.fx.annotation.property.TReadOnlyDoubleProperty;
import org.tedros.fx.annotation.property.TStringProperty;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.shape.TShape;
import org.tedros.fx.builder.ITControlBuilder;
import org.tedros.fx.builder.ITFieldBuilder;
import org.tedros.fx.builder.TTextBuilder;
import org.tedros.fx.control.TText.TTextStyle;

import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.shape.Shape;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;

/**
 * <pre>
 * The Text class defines a node that displays a text. Paragraphs are separated by '\n' 
 * and the text is wrapped on paragraph boundaries.
 * </pre>
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TText {
	
	/**
	 *<pre>
	 * The builder of type {@link ITControlBuilder} for this component.
	 * 
	 *  Default value: {@link TTextBuilder}
	 *</pre> 
	 * */
	public Class<? extends ITFieldBuilder> builder() default TTextBuilder.class;
	
	/**
	* <pre>
	* The parser class for this annotation
	* 
	* Default value: {TRequiredNumeberFieldParser.class, TNumberFieldParser.class}
	* </pre>
	* */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {TTextParser.class};
	
	/**
	* <pre>
	* {@link TText} Class
	* 
	*  Sets the value of the property tTextStyle. 
	*  
	*  Property description: 
	*  
	*  Defines text tedros style. 
	*  
	*  Default value: TTextStyle.CUSTOM
	* </pre>
	**/
	public TTextStyle textStyle();// default TTextStyle.CUSTOM;
	
	/**
	* <pre>
	* The {@link Node} settings.
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
	*  Default value: TFont
	* </pre>
	**/
	public TFont font() default @TFont;

	/**
	* <pre>
	* {@link Text} Class
	* 
	*  Sets the value of the property textOrigin. 
	*  
	*  Property description: 
	*  
	*  Defines the origin of text coordinate system in local coordinates. 
	*  Note: in case multiple rows are rendered VPos.BASELINE and VPos.TOP 
	*  define the origin of the top row while VPos.BOTTOM defines the origin 
	*  of the bottom row. 
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
	*  
	*  Logical bounds is a more appropriate default for text than the visual bounds. 
	*  
	*  See TextBoundsType for more information. 
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
	*  Defines a width constraint for the text in user space coordinates, e.g. pixels, 
	*  not glyph or character count. If the value is > 0 text will be line wrapped as 
	*  needed to satisfy this constraint. 
	*  
	*  Default value: 0
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
	*  Note: In the case of a single line of text, where the width 
	*  of the node is determined by the width of the text, the alignment 
	*  setting has no effect. 
	*  
	*  Default value: TextAlignment.LEFT
	* </pre>
	**/
	public TextAlignment textAlignment() default TextAlignment.CENTER;

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
	
	// PROPERTY
	
	/**
	* <pre>
	* {@link Text} Class
	* 
	*  Defines text string that is to be displayed. 
	* </pre>
	**/
	public TStringProperty textProperty() default @TStringProperty(parse = false);

	/**
	* <pre>
	* {@link Text} Class
	* 
	*  Defines the X coordinate of text origin. 
	* </pre>
	**/
	public TDoubleProperty xProperty() default @TDoubleProperty(parse = false);

	/**
	* <pre>
	* {@link Text} Class
	* 
	*  Defines the Y coordinate of text origin. 
	* </pre>
	**/
	public TDoubleProperty yProperty() default @TDoubleProperty(parse = false);

	/**
	* <pre>
	* {@link Text} Class
	* 
	*  Defines the font of text. 
	* </pre>
	**/
	public TObjectProperty fontProperty() default @TObjectProperty(parse = false);

	/**
	* <pre>
	* {@link Text} Class
	* 
	*  Defines the origin of text coordinate system in local coordinates. 
	*  Note: in case multiple rows are rendered VPos.BASELINE and VPos.TOP define 
	*  the origin of the top row while VPos.BOTTOM defines the origin of the bottom row. 
	* </pre>
	**/
	public TObjectProperty textOriginProperty() default @TObjectProperty(parse = false);

	/**
	* <pre>
	* {@link Text} Class
	* 
	*  Determines how the bounds of the text node are calculated. 
	*  Logical bounds is a more appropriate default for text than the visual bounds
	* </pre>
	**/
	public TObjectProperty boundsTypeProperty() default @TObjectProperty(parse = false);

	/**
	* <pre>
	* {@link Text} Class
	* 
	*  Defines a width constraint for the text in user space coordinates, e.g. pixels, 
	*  not glyph or character count. If the value is > 0 text will be line wrapped as 
	*  needed to satisfy this constraint. 
	* </pre>
	**/
	public TDoubleProperty wrappingWidthProperty() default @TDoubleProperty(parse = false);

	/**
	* <pre>
	* {@link Text} Class
	* 
	*  Defines if each line of text should have a line below it. 
	* </pre>
	**/
	public TBooleanProperty underlineProperty() default @TBooleanProperty(parse = false);

	/**
	* <pre>
	* {@link Text} Class
	* 
	*  Defines if each line of text should have a line through it. 
	* </pre>
	**/
	public TBooleanProperty strikethroughProperty() default @TBooleanProperty(parse = false);

	/**
	* <pre>
	* {@link Text} Class
	* 
	*  Defines horizontal text alignment in the bounding box. The width of the bounding 
	*  box is defined by the widest row. 
	*  
	*  Note: In the case of a single line of text, where the width of the node is determined
	*   by the width of the text, the alignment setting has no effect. 
	* </pre>
	**/
	public TObjectProperty textAlignmentProperty() default @TObjectProperty(parse = false);

	/**
	* <pre>
	* {@link Text} Class
	* 
	*  The 'alphabetic' (or roman) baseline offset from the Text node's layoutBounds.minY 
	*  location. The value typically corresponds to the max ascent of the font
	* </pre>
	**/
	public TReadOnlyDoubleProperty baselineOffsetProperty() default @TReadOnlyDoubleProperty(parse = false);

	/**
	* <pre>
	* {@link Text} Class
	* 
	*  Specifies a requested font smoothing type : gray or LCD. The width of the bounding box 
	*  is defined by the widest row. 
	*  
	*  Note: LCD mode doesn't apply in numerous cases, such as various compositing modes, where 
	*  effects are applied and very large glyphs. 
	* </pre>
	**/
	public TObjectProperty fontSmoothingTypeProperty() default @TObjectProperty(parse = false);
	
	/**
	* <pre>
	*  Sets the reader mode;
	*  
	*  Default value: true
	* </pre>
	**/
	public boolean reader() default true;
	
	/**
	 * Specifies a converter class. 
	 * */
	public TConverter converter() default @TConverter(type=org.tedros.fx.converter.TConverter.class, parse = false);
	
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
