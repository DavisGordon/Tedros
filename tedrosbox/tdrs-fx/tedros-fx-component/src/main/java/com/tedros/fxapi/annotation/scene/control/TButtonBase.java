package com.tedros.fxapi.annotation.scene.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javafx.event.ActionEvent;
import javafx.scene.control.ButtonBase;

import com.tedros.fxapi.annotation.parser.ITAnnotationParser;
import com.tedros.fxapi.annotation.parser.TButtonBaseParser;
import com.tedros.fxapi.builder.ITEventHandlerBuilder;
import com.tedros.fxapi.builder.NullActionEventBuilder;

/**
 * <pre>
 * The Base class annotation for button-like UI Controls, including Hyperlinks, Buttons, ToggleButtons, 
 * CheckBoxes, and RadioButtons. The primary contribution of ButtonBase is providing a 
 * consistent API for handling the concept of button "arming". In UIs, a button will typically 
 * only "fire" if some user gesture occurs while the button is "armed". For example, a Button 
 * may be armed if the mouse is pressed and the Button is enabled and the mouse is over the button. 
 * In such a situation, if the mouse is then released, then the Button is "fired", meaning its 
 * action takes place.
 * </pre>
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface TButtonBase {

	/**
	 * <pre>
	 * The parser class for this annotation.
	 * 
	 * Default value: TComboBoxBaseParser.class
	 * </pre>
	 * */
	public Class<? extends ITAnnotationParser<TButtonBase, ButtonBase>>[] parser() default {TButtonBaseParser.class};
	
	/**
	* <pre>
	* {@link ButtonBase} Class
	* 
	*  Sets the value of the property onAction. 
	*  
	*  Property description: 
	*  
	*  The button's action, which is invoked whenever the button is fired. 
	*  This may be due to the user clicking on the button with the mouse, 
	*  or by a touch event, or by a key press, or if the developer programmatically 
	*  invokes the fire() method.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<ActionEvent>> onAction() default NullActionEventBuilder.class;
	
}
