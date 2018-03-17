package com.tedros.fxapi.annotation.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Control;
import javafx.scene.control.Labeled;
import javafx.scene.control.OverrunStyle;
import javafx.scene.text.TextAlignment;

import com.tedros.fxapi.annotation.TAnnotationDefaultValue;
import com.tedros.fxapi.annotation.parser.TAnnotationParser;
import com.tedros.fxapi.annotation.parser.TLabelParser;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.text.TFont;
import com.tedros.fxapi.builder.ITNodeBuilder;
import com.tedros.fxapi.domain.TDefaultValues;


/**
 * <pre>
 * Config a {@link com.tedros.fxapi.control.TLabel} component.
 * 
 * Label is a non-editable text control. A Label is useful for displaying text that is required to fit within a specific space, 
 * and thus may need to use an ellipsis or truncation to size the string to fit. Labels also are useful in that they can have 
 * mnemonics which, if used, will send focus to the Control listed as the target of the labelFor property.
 * 
 * Label sets focusTraversable to false.
 * </pre>
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TLabel {
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TLabelParser.class}
	 * </pre>
	 * */
	public Class<? extends TAnnotationParser<TLabel, Labeled>> parser() default TLabelParser.class;
	
	
	/**
	 * <pre>
	 * The {@link Node} settings for this Label
	 * </pre>
	 * */
	public TNode node() default @TNode(parse=false);
	
	/**
	 * <pre>
	 * The {@link Control} settings for this Label
	 * 
	 * Default value: @TControl(prefWidth=250) 
	 * </pre>
	 * */
	public TControl control() default @TControl(prefWidth=TDefaultValues.LABEL_WIDTH, parse = true);
	
	/**
	 * <pre>
	 * Set the positioning of the label relative to the control.
	 * 
	 * Default value: TLabelPosition.TOP
	 * </pre>
	 * */
	public TLabelPosition position() default TLabelPosition.DEFAULT; 
	
	/* LABELED */
	
	/**
	* <pre>
	* {@link Labeled} Class
	* 
	*  Sets the value of the property text. 
	*  
	*  Property description: 
	*  
	*  The text to display in the label. The text may be null.
	*  
	*  Default value: Empty string
	* </pre>
	**/
	public String text() default "";

	/**
	* <pre>
	* {@link Labeled} Class
	* 
	*  Sets the value of the property alignment. 
	*  
	*  Property description: 
	*  
	*  Specifies how the text and graphic within the Labeled should be aligned 
	*  when there is empty space within the Labeled.
	*  
	*  Default value: Pos.CENTER_LEFT
	*  
	* </pre>
	**/
	public Pos alignment() default Pos.CENTER_LEFT;

	/**
	* <pre>
	* {@link Labeled} Class
	* 
	*  Sets the value of the property textAlignment. 
	*  
	*  Property description: 
	*  
	*  Specifies the behavior for lines of text when text is multiline Unlike 
	*  contentDisplayProperty() which affects the graphic and text, this setting 
	*  only affects multiple lines of text relative to the text bounds.
	*  
	*  Default value: TextAlignment.LEFT
	* </pre>
	**/
	public TextAlignment textAlignment() default TextAlignment.LEFT;

	/**
	* <pre>
	* {@link Labeled} Class
	* 
	*  Sets the value of the property textOverrun. 
	*  
	*  Property description: 
	*  
	*  Specifies the behavior to use if the text of the Labeled exceeds the available 
	*  space for rendering the text.
	*  
	*  Default value: OverrunStyle.ELLIPSIS
	* </pre>
	**/
	public OverrunStyle textOverrun() default OverrunStyle.ELLIPSIS;

	/**
	* <pre>
	* {@link Labeled} Class
	* 
	*  Sets the value of the property ellipsisString. 
	*  
	*  Property description: 
	*  
	*  Specifies the string to display for the ellipsis when text is truncated. 
	*  
	*  Examples:
	*  "..."        - Default value for most locales 
	*  " . . . " 
	*  " [...] " 
	*  "\u2026"     - The Unicode ellipsis character 
	*  ""           - No ellipsis, just display the truncated string 
	*  
	*  Note that not all fonts support all Unicode characters.
	*  
	*  Default value: "..."
	* </pre>
	**/
	public String ellipsisString() default TDefaultValues.LABEL_ELLIPSISSTRING;

	/**
	* <pre>
	* {@link Labeled} Class
	* 
	*  Sets the value of the property wrapText. 
	*  
	*  Property description: 
	*  
	*  If a run of text exceeds the width of the Labeled, then this variable 
	*  indicates whether the text should wrap onto another line.
	*  
	*  Default value: true
	* </pre>
	**/
	public boolean wrapText() default TDefaultValues.LABEL_WRAPTEXT;;

	/**
	* <pre>
	* {@link Labeled} Class
	* 
	*  Sets the value of the property font. 
	*  
	*  Property description: 
	*  
	*  The default font to use for text in the Labeled. If the Label's text is 
	*  rich text then this font may or may not be used depending on the font 
	*  information embedded in the rich text, but in any case where a default 
	*  font is required, this font will be used.
	*  
	*  Default value: @TFont(family="System", weight=FontWeight.NORMAL, posture=FontPosture.REGULAR, size=12)
	* </pre>
	**/
	public TFont font() default @TFont();

	/**
	* <pre>
	* {@link Labeled} Class
	* 
	*  Sets the value of the property graphic. 
	*  
	*  Property description: 
	*  
	*  An optional icon for the Labeled. This can be positioned relative to the 
	*  text by using setContentDisplay(javafx.scene.control.ContentDisplay). 
	*  The node specified for this variable cannot appear elsewhere in the scene 
	*  graph, otherwise the IllegalArgumentException is thrown. 
	*  
	*  See the class description of Node for more detail.
	* </pre>
	**/
	@SuppressWarnings("rawtypes")
	public Class<? extends ITNodeBuilder> graphic() default ITNodeBuilder.class;

	/**
	* <pre>
	* {@link Labeled} Class
	* 
	*  Sets the value of the property underline. 
	*  
	*  Property description: 
	*  
	*  Whether all text should be underlined.
	*  
	*  Default value: false
	* </pre>
	**/
	public boolean underline()  default TDefaultValues.LABEL_UNDERLINE;

	/**
	* <pre>
	* {@link Labeled} Class
	* 
	*  Sets the value of the property contentDisplay. 
	*  
	*  Property description: 
	*  
	*  Specifies the positioning of the graphic relative to the text.
	*  
	*  Default value: ContentDisplay.LEFT
	* </pre>
	**/
	public ContentDisplay contentDisplay() default ContentDisplay.LEFT;

	/**
	* <pre>
	* {@link Labeled} Class
	* 
	*  Sets the value of the property graphicTextGap. 
	*  
	*  Property description: 
	*  
	*  The amount of space between the graphic and text
	* </pre>
	**/
	public double graphicTextGap() default TAnnotationDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION;

	/**
	* <pre>
	* {@link Labeled} Class
	* 
	*  Sets the value of the property textFill. 
	*  
	*  Property description: 
	*  
	*  The hexadecimal color value used to fill the text.
	*  
	*  Default value: empty string
	* </pre>
	**/
	public String textFill() default "";

	/**
	* <pre>
	* {@link Labeled} Class
	* 
	*  Sets the value of the property mnemonicParsing. 
	*  
	*  Property description: 
	*  
	*  MnemonicParsing property to enable/disable text parsing. 
	*  If this is set to true, then the Label text will be parsed to see 
	*  if it contains the mnemonic parsing character '_'. 
	*  When a mnemonic is detected the key combination will be determined 
	*  based on the succeeding character, and the mnemonic added. 
	*  
	*  The default value for Labeled is false, but it is enabled by default on some Controls.
	* </pre>
	**/
	public boolean mnemonicParsing() default TDefaultValues.LABEL_MNEMONICPARSING;
	
	
}
