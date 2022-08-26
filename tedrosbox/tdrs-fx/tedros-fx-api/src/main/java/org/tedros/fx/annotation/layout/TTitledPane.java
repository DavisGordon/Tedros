/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 08/11/2013
 */
package org.tedros.fx.annotation.layout;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.api.parser.ITAnnotationParser;
import org.tedros.fx.annotation.TAnnotationDefaultValue;
import org.tedros.fx.annotation.parser.TLabelParser;
import org.tedros.fx.annotation.parser.TTitledPaneParser;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.annotation.text.TFont;
import org.tedros.fx.builder.ITNodeBuilder;
import org.tedros.fx.domain.TDefaultValues;
import org.tedros.fx.domain.TLayoutType;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Control;
import javafx.scene.control.Labeled;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.TitledPane;
import javafx.scene.text.TextAlignment;

/**
 * <pre>
 * Build a {@link TitledPane}.
 * 
 * Example: 
 * 
 *  <i>@</i>TAccordion(panes = {<i>@</i><strong>TTitledPane</strong>(text = "Group Fields", fields={"<strong style="color:red">optionsField</strong>", "<strong style="color:blue">passField"</strong>, "<strong style="color:green">integerField</strong>"})})
 *  <i>@</i>TLabel(text="Choose a book:", control=<i>@</i>TControl(prefWidth=500))
 *  <i>@</i>TVerticalRadioGroup(spacing=5, required=true, alignment= Pos.CENTER_LEFT
 *          ,radioButtons={
 *          <i>@</i>TRadioButton(userData="A", text="The Java Programming Language"),
 *          <i>@</i>TRadioButton(userData="B", text="The Java Language Specification")
 *  })
 *  private SimpleStringProperty <strong style="color:red">optionsField</strong>;
 *
 *  <i>@</i>TLabel(text="Password:", control=<i>@</i>TControl(prefWidth=500))
 *  <i>@</i>TPasswordField(required=true, maxLength=6)
 *  private SimpleStringProperty <strong style="color:blue">passField</strong>;
 *		
 *  <i>@</i>TLabel(text="Number field", control=<i>@</i>TControl(prefWidth=500))
 *  <i>@</i>TIntegerField(zeroValidation=TZeroValidation.MORE_THAN_ZERO, control=<i>@</i>TControl(tooltip="Max value: "+Integer.MAX_VALUE))
 *  private SimpleIntegerProperty <strong style="color:green">integerField</strong>;
 * 
 * Oracle documentation:
 *
 * A TitledPane is a panel with a title that can be opened and closed.
 *
 * The panel in a TitledPane can be any Node such as UI controls or groups 
 * of nodes added to a layout container.
 * 
 * It is not recommended to set the MinHeight, PrefHeight, or MaxHeight for 
 * this control. Unexpected behavior will occur because the TitledPane's height 
 * changes when it is opened or closed.
 *
 *</pre>
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface TTitledPane  {
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TTitledPaneParser.class, TLabelParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {TTitledPaneParser.class, TLabelParser.class};
	
	/**
	 * <pre>
	 * The {@link Node} settings.
	 * </pre>
	 * */
	public TNode node() default @TNode(parse = false);
	
	/**
	 * <pre>
	 * The {@link Control} settings. 
	 * </pre>
	 * */
	public TControl control() default @TControl(parse = false);
	
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
	
	/**
	* <pre>
	* {@link TitledPane} Class
	* 
	*  The content of the TitlePane which can be any Node such as UI controls or groups of nodes added to a layout container. Parameters: value - The content for this TitlePane.
	* </pre>
	**/
	//public Node content();

	/**
	* <pre>
	* {@link TitledPane} Class
	* 
	*  Sets the expanded state of the TitledPane. 
	*  
	*  The default is true.
	* </pre>
	**/
	public boolean expanded() default true;

	/**
	* <pre>
	* {@link TitledPane} Class
	* 
	*  Specifies how the TitledPane should open and close. The panel will be animated out when this value is set to true. 
	*  
	*  The default is true.
	* </pre>
	**/
	public boolean animated() default true;

	/**
	* <pre>
	* {@link TitledPane} Class
	* 
	*  Specifies if the TitledPane can be collapsed. 
	*  
	*  The default is true.
	* </pre>
	**/
	public boolean collapsible() default true;

	/**
	 * <pre>
	 * An array of fields name to add in the {@link TitledPane}
	 * </pre>
	 * */
	public String[] fields();
	
	/**
	 * <pre>
	 * Specifies a layout to be used inside the {@link TitledPane}
	 * 
	 * Default value:
	 * 
	 * TLayoutType.VBOX
	 * </pre>
	 * */
	public TLayoutType layoutType() default TLayoutType.VBOX;
	
}
