package com.tedros.fxapi.annotation.scene.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBase;

import com.tedros.fxapi.annotation.parser.ITAnnotationParser;
import com.tedros.fxapi.annotation.parser.TComboBoxBaseParser;
import com.tedros.fxapi.annotation.property.TBooleanProperty;
import com.tedros.fxapi.annotation.property.TObjectProperty;
import com.tedros.fxapi.annotation.property.TReadOnlyBooleanProperty;
import com.tedros.fxapi.annotation.property.TStringProperty;
import com.tedros.fxapi.builder.ITBuilder;
import com.tedros.fxapi.builder.ITEventHandlerBuilder;
import com.tedros.fxapi.builder.NullActionEventBuilder;
import com.tedros.fxapi.builder.NullEventBuilder;

/**
 * <pre>
 * The annotation of the abstract base class for ComboBox-like controls. 
 * A ComboBox typically has a button that, when clicked, will pop up some 
 * means of allowing a user to select one or more values (depending on the 
 * implementation). This base class makes no assumptions about what happens 
 * when the show() and hide() methods are called, however commonly this results 
 * in either a popup or dialog appearing that allows for the user to provide 
 * the required information.
 * 
 * A ComboBox has a value property that represents the current user input. 
 * This may be based on a selection from a drop-down list, or it may be from 
 * user input when the ComboBox is editable.
 * 
 * An editable ComboBox is one which provides some means for an end-user to 
 * provide input for values that are not otherwise options available to them. 
 * For example, in the ComboBox implementation, an editable ComboBox provides 
 * a TextField that may be typed into. As mentioned above, when the user commits 
 * textual input into the textfield (commonly by pressing the Enter keyboard key), 
 * the value property will be updated.
 * 
 * The purpose of the separation between this class and, say, ComboBox is to 
 * allow for ComboBox-like controls that do not necessarily pop up a list of 
 * items. Examples of other implementations include color pickers, calendar pickers, etc. 
 * The ComboBox class provides the default, and most commonly expected implementation. 
 * Refer to that classes javadoc for more information.
 * 
 * See Also:
 * {@link ComboBox}
 * </pre>
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
public @interface TComboBoxBase {

	/**
	 * <pre>
	 * The parser class for this annotation.
	 * 
	 * Default value: TComboBoxBaseParser.class
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser<TComboBoxBase, ComboBoxBase>>[] parser() default {TComboBoxBaseParser.class};
	
	/**
	* <pre>
	* {@link ComboBoxBase} Class
	* 
	*  Sets the value of the property value. 
	*  
	*  Property description: 
	*  
	*  The value of this ComboBox is defined as the selected item if the input 
	*  is not editable, or if it is editable, the most recent user action: either 
	*  the value input by the user, or the last selected item.
	* </pre>
	**/
	public Class<? extends ITBuilder> value() default ITBuilder.class;

	/**
	* <pre>
	* {@link ComboBoxBase} Class
	* 
	*  Sets the value of the property editable. 
	*  
	*  Property description: 
	*  
	*  Specifies whether the ComboBox allows for user input. When editable is true, 
	*  the ComboBox has a text input area that a user may type in to. This input is 
	*  then available via the value property. Note that when the editable property 
	*  changes, the value property is reset, along with any other relevant state.
	* </pre>
	**/
	public boolean editable() default false;

	/**
	* <pre>
	* {@link ComboBoxBase} Class
	* 
	*  Sets the value of the property promptText. 
	*  
	*  Property description: 
	*  
	*  The ComboBox prompt text to display, or null if no prompt text is displayed. 
	*  Prompt text is not displayed in all circumstances, it is dependent upon the 
	*  subclasses of ComboBoxBase to clarify when promptText will be shown.
	* </pre>
	**/
	public String promptText() default "";

	/**
	* <pre>
	* {@link ComboBoxBase} Class
	* 
	*  Sets the value of the property onAction. 
	*  
	*  Property description: 
	*  
	*  The ComboBox action, which is invoked whenever the ComboBox value property 
	*  is changed. This may be due to the value property being programmatically changed, 
	*  when the user selects an item in a popup list or dialog, or, in the case of 
	*  editable ComboBoxes, it may be when the user provides their own input (be that 
	*  via a TextField or some other input mechanism.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<ActionEvent>> onAction() default NullActionEventBuilder.class;

	/**
	* <pre>
	* {@link ComboBoxBase} Class
	* 
	*  Called just prior to the ComboBoxBase popup/display being shown, Since: 2.2
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<Event>> onShowing() default NullEventBuilder.class;

	/**
	* <pre>
	* {@link ComboBoxBase} Class
	* 
	*  Called just after the ComboBoxBase popup/display is shown. Since: 2.2
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<Event>> onShown() default NullEventBuilder.class;

	/**
	* <pre>
	* {@link ComboBoxBase} Class
	* 
	*  Called just prior to the ComboBox popup/display being hidden. Since: 2.2
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<Event>> onHiding() default NullEventBuilder.class;

	/**
	* <pre>
	* {@link ComboBoxBase} Class
	* 
	*  Called just after the ComboBoxBase popup/display has been hidden. Since: 2.2
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<Event>> onHidden() default NullEventBuilder.class;
	
	// PROPERTYS
	
	/**
	* <pre>
	* {@link ComboBoxBase} Class
	* 
	*  The value of this ComboBox is defined as the selected item if the input is not editable, 
	*  or if it is editable, the most recent user action: either the value input by the user, 
	*  or the last selected item
	* </pre>
	**/
	public TObjectProperty valueProperty() default @TObjectProperty(parse = false);

	/**
	* <pre>
	* {@link ComboBoxBase} Class
	* 
	*  Specifies whether the ComboBox allows for user input. When editable is true, the ComboBox 
	*  has a text input area that a user may type in to. This input is then available via the value 
	*  property. Note that when the editable property changes, the value property is reset, along 
	*  with any other relevant state
	* </pre>
	**/
	public TBooleanProperty editableProperty() default @TBooleanProperty(parse = false);

	/**
	* <pre>
	* {@link ComboBoxBase} Class
	* 
	*  Represents the current state of the ComboBox popup, and whether it is 
	*  currently visible on screen (although it may be hidden behind other windows)
	* </pre>
	**/
	public TReadOnlyBooleanProperty showingProperty() default @TReadOnlyBooleanProperty(parse = false);

	/**
	* <pre>
	* {@link ComboBoxBase} Class
	* 
	*  The ComboBox prompt text to display, or null if no prompt text is displayed. 
	*  Prompt text is not displayed in all circumstances, it is dependent upon the 
	*  subclasses of ComboBoxBase to clarify when promptText will be shown
	* </pre>
	**/
	public TStringProperty promptTextProperty() default @TStringProperty(parse = false);

	/**
	* <pre>
	* {@link ComboBoxBase} Class
	* 
	*  Indicates that the ComboBox has been "armed" such that a mouse release will 
	*  cause the ComboBox show() method to be invoked. This is subtly different from 
	*  pressed. Pressed indicates that the mouse has been pressed on a Node and has not 
	*  yet been released. arm however also takes into account whether the mouse is 
	*  actually over the ComboBox and pressed
	* </pre>
	**/
	public TBooleanProperty armedProperty() default @TBooleanProperty(parse = false);

	/**
	* <pre>
	* {@link ComboBoxBase} Class
	* 
	*  The ComboBox action, which is invoked whenever the ComboBox value property is changed. 
	*  This may be due to the value property being programmatically changed, when the user 
	*  selects an item in a popup list or dialog, or, in the case of editable ComboBoxes, 
	*  it may be when the user provides their own input (be that via a TextField or some 
	*  other input mechanism
	* </pre>
	**/
	public TObjectProperty onActionProperty() default @TObjectProperty(parse = false);

	/**
	* <pre>
	* {@link ComboBoxBase} Class
	* 
	*  See Also: getOnShowing(), setOnShowing(EventHandler)
	* </pre>
	**/
	public TObjectProperty onShowingProperty() default @TObjectProperty(parse = false);

	/**
	* <pre>
	* {@link ComboBoxBase} Class
	* 
	*  See Also: getOnShown(), setOnShown(EventHandler)
	* </pre>
	**/
	public TObjectProperty onShownProperty() default @TObjectProperty(parse = false);

	/**
	* <pre>
	* {@link ComboBoxBase} Class
	* 
	*  See Also: getOnHiding(), setOnHiding(EventHandler)
	* </pre>
	**/
	public TObjectProperty onHidingProperty() default @TObjectProperty(parse = false);

	/**
	* <pre>
	* {@link ComboBoxBase} Class
	* 
	*  See Also: getOnHidden(), setOnHidden(EventHandler)
	* </pre>
	**/
	public TObjectProperty onHiddenProperty() default @TObjectProperty(parse = false);
	
	/**
	 * <pre>
	 * Force the parser execution 
	 * </pre>
	 * */
	public boolean parse();
}
