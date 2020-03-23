package com.tedros.fxapi.annotation.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javafx.scene.control.TextInputControl;

import com.tedros.fxapi.annotation.parser.ITAnnotationParser;
import com.tedros.fxapi.annotation.parser.TTextInputControlParse;
import com.tedros.fxapi.annotation.property.TStringProperty;

/**
 * <pre>
 * Set the {@link TextInputControl} properties.
 * </pre>
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TTextInputControl {
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TTextInputControlParse.class}
	 * </pre>
	 * */
	public Class<? extends ITAnnotationParser<TTextInputControl, TextInputControl>>[] parser() default {TTextInputControlParse.class};
	
	/**
	* <pre>
	* {@link TextInputControl} Class
	* 
	*  Sets the value of the property promptText. 
	*  
	*  Property description: 
	*  
	*  The prompt text to display in the TextInputControl, 
	*  or null if no prompt text is displayed.
	*  
	*  Default value: Empty string
	* </pre>
	**/
	public java.lang.String promptText() default "";

	/**
	* <pre>
	* {@link TextInputControl} Class
	* 
	*  Sets the value of the property text. 
	*  
	*  Property description: 
	*  
	*  The textual content of this TextInputControl.
	*  
	*  Default value: Empty string
	* </pre>
	**/
	public java.lang.String text() default "";

	/**
	* <pre>
	* {@link TextInputControl} Class
	* 
	*  Sets the value of the property editable. 
	*  
	*  Property description: 
	*  
	*  Indicates whether this TextInputControl can be edited by the user.
	*  
	*  Default value: true
	* </pre>
	**/
	public boolean editable() default true;
	
	/**
	 * <pre>
	 * Force the parser execution 
	 * </pre>
	 * */
	public boolean parse();
	
	/**
	* <pre>
	* {@link TextInputControl} Class
	* 
	*  The prompt text to display in the TextInputControl, or null if no prompt text is displayed
	* </pre>
	**/
	public TStringProperty promptTextProperty() default @TStringProperty(parse = false);

	/**
	* <pre>
	* {@link TextInputControl} Class
	* 
	*  The textual content of this TextInputControl
	* </pre>
	**/
	public TStringProperty textProperty() default @TStringProperty(parse = false);

	/**
	* <pre>
	* {@link TextInputControl} Class
	* 
	*  The number of characters in the text input
	* </pre>
	**//*
	public TReadOnlyIntegerProperty lengthProperty() default @TReadOnlyIntegerProperty(parse = false);

	*//**
	* <pre>
	* {@link TextInputControl} Class
	* 
	*  Indicates whether this TextInputControl can be edited by the user
	* </pre>
	**//*
	public TBooleanProperty editableProperty() default @TBooleanProperty(parse = false);

	*//**
	* <pre>
	* {@link TextInputControl} Class
	* 
	*  The current selection
	* </pre>
	**//*
	public TReadOnlyObjectProperty selectionProperty() default @TReadOnlyObjectProperty(parse = false);

	*//**
	* <pre>
	* {@link TextInputControl} Class
	* 
	*  Defines the characters in the TextInputControl which are selected See Also: getSelectedText()
	* </pre>
	**//*
	public TReadOnlyStringProperty selectedTextProperty() default @TReadOnlyStringProperty(parse = false);

	*//**
	* <pre>
	* {@link TextInputControl} Class
	* 
	*  The anchor of the text selection. The anchor and caretPosition make up the selection range. Selection must always be specified in terms of begin <= end, but anchor may be less than, equal to, or greater than the caretPosition. Depending on how the user selects text, the anchor might represent the lower or upper bound of the selection
	* </pre>
	**//*
	public TReadOnlyIntegerProperty anchorProperty() default @TReadOnlyIntegerProperty(parse = false);

	*//**
	* <pre>
	* {@link TextInputControl} Class
	* 
	*  The current position of the caret within the text. The anchor and caretPosition make up the selection range. Selection must always be specified in terms of begin <= end, but anchor may be less than, equal to, or greater than the caretPosition. Depending on how the user selects text, the caretPosition might represent the lower or upper bound of the selection
	* </pre>
	**//*
	public TReadOnlyIntegerProperty caretPositionProperty() default @TReadOnlyIntegerProperty(parse = false);*/



}
