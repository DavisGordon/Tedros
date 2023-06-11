package org.tedros.fx.annotation.scene.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.fx.annotation.TDefaultValue;
import org.tedros.fx.builder.ITBuilder;

import javafx.scene.Node;

/**
 * <pre>
 * Base class for all user interface controls. A "Control" is a node in the scene graph which can 
 * be manipulated by the user. Controls provide additional variables and behaviors beyond those 
 * of Node to support common user interactions in a manner which is consistent and predictable for 
 * the user.
 * 
 * Additionally, controls support explicit skinning to make it easy to leverage the functionality of 
 * a control while customizing its appearance.
 * 
 * See specific Control subclasses for information on how to use individual types of controls.
 * 
 * Most controls have their focusTraversable property set to true by default, however read-only controls 
 * such as Label and ProgressIndicator, and some controls that are containers ScrollPane and ToolBar do not. 
 * Consult individual control documentation for details.
 * </pre>
 * **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface TControlDefaultSetting {
	
	/**
	* <pre>
	* {@link Node} property
	* 
	* Sets the value of the property tooltip. 
	* 
	* Property description: 
	* 
	* The ToolTip for this control.
	* </pre>
	**/
	public Class<? extends ITBuilder> tooltip() default ITBuilder.class;

	/**
	* <pre>
	* {@link Node} property
	* 
	* Sets the value of the property contextMenu. 
	* 
	* Property description: 
	* 
	* The ContextMenu to show for this control.
	* </pre>
	**/
	public Class<? extends ITBuilder> contextMenu() default ITBuilder.class;

	/**
	* <pre>
	* {@link Node} property
	* 
	* Sets the value of the property minWidth. 
	* 
	* Property description: 
	* 
	* Property for overriding the control's computed minimum width. 
	* This should only be set if the control's internally computed 
	* minimum width doesn't meet the application's layout needs. 
	* Defaults to the USE_COMPUTED_SIZE flag, which means that 
	* getMinWidth(forHeight) will return the control's internally computed 
	* minimum width. Setting this value to the USE_PREF_SIZE flag will 
	* cause getMinWidth(forHeight) to return the control's preferred width, 
	* enabling applications to easily restrict the resizability of the control.
	* </pre>
	**/
	public double minWidth() default TDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION;

	/**
	* <pre>
	* {@link Node} property
	* 
	* Sets the value of the property minHeight. 
	* 
	* Property description: 
	* 
	* Property for overriding the control's computed minimum height. This should only 
	* be set if the control's internally computed minimum height doesn't meet the 
	* application's layout needs. Defaults to the USE_COMPUTED_SIZE flag, which means 
	* that getMinHeight(forWidth) will return the control's internally computed minimum height. 
	* Setting this value to the USE_PREF_SIZE flag will cause getMinHeight(forWidth) to return 
	* the control's preferred height, enabling applications to easily restrict the resizability 
	* of the control.
	* </pre>
	**/
	public double minHeight() default TDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION;

	/**
	* <pre>
	* {@link Node} property
	* 
	* Convenience method for overriding the control's computed minimum width and height. 
	* This should only be called if the control's internally computed minimum size doesn't 
	* meet the application's layout needs. Parameters: minWidth - the override value for 
	* minimum width minHeight - the override value for minimum height 
	* 
	* See Also: minWidth(double), minHeight(double)
	* </pre>
	**/
	public TSize minSize() default @TSize(width=TDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION, height=TDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION);

	/**
	* <pre>
	* {@link Node} property
	* 
	* Sets the value of the property prefWidth.
	* 
	*  Property description: 
	*  
	*  Property for overriding the control's computed preferred width. This should only be set if 
	*  the control's internally computed preferred width doesn't meet the application's layout needs. 
	*  Defaults to the USE_COMPUTED_SIZE flag, which means that getPrefWidth(forHeight) will return 
	*  the control's internally computed preferred width.
	* </pre>
	**/
	public double prefWidth() default TDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION;

	/**
	* <pre>
	* {@link Node} property
	* 
	* Sets the value of the property prefHeight. 
	* 
	* Property description: 
	* 
	* Property for overriding the control's computed preferred height. This should only be set if the 
	* control's internally computed preferred height doesn't meet the application's layout needs. 
	* Defaults to the USE_COMPUTED_SIZE flag, which means that getPrefHeight(forWidth) will return the 
	* control's internally computed preferred width.
	* </pre>
	**/
	public double prefHeight() default TDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Convenience method for overriding the control's computed preferred width and height. This should 
	*  only be called if the control's internally computed preferred size doesn't meet the application's 
	*  layout needs. 
	*  
	*  Parameters: prefWidth - the override value for preferred width prefHeight - the override value 
	*  for preferred height 
	*  
	*  See Also: setPrefWidth(double), setPrefHeight(double)
	* </pre>
	**/
	public TSize prefSize() default @TSize(width=TDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION, height=TDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION);

	/**
	* <pre>
	* {@link Node} property
	* 
	* Sets the value of the property maxWidth. 
	* 
	* Property description: 
	* 
	* Property for overriding the control's computed maximum width. This should only be set if the control's 
	* internally computed maximum width doesn't meet the application's layout needs. 
	* Defaults to the USE_COMPUTED_SIZE flag, which means that getMaxWidth(forHeight) will return the control's 
	* internally computed maximum width. Setting this value to the USE_PREF_SIZE flag will cause getMaxWidth(forHeight) 
	* to return the control's preferred width, enabling applications to easily restrict the resizability of the control.
	* </pre>
	**/
	public double maxWidth() default TDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION;

	/**
	* <pre>
	* {@link Node} property
	*  
	* Sets the value of the property maxHeight. 
	* 
	* Property description: 
	* 
	* Property for overriding the control's computed maximum height. This should only be set if the control's internally 
	* computed maximum height doesn't meet the application's layout needs. Defaults to the USE_COMPUTED_SIZE flag, which 
	* means that getMaxHeight(forWidth) will return the control's internally computed maximum height. 
	* Setting this value to the USE_PREF_SIZE flag will cause getMaxHeight(forWidth) to return the control's preferred height, 
	* enabling applications to easily restrict the resizability of the control.
	* </pre>
	**/
	public double maxHeight() default TDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION;

	/**
	* <pre>
	* {@link Node} property
	* 
	* Convenience method for overriding the control's computed maximum width and height. This should only be called if the 
	* control's internally computed maximum size doesn't meet the application's layout needs. 
	* 
	* Parameters: maxWidth - the override value for maximum width maxHeight - the override value for maximum height 
	* 
	* See Also: setMaxWidth(double), setMaxHeight(double)
	* </pre>
	**/
	public TSize maxSize() default @TSize(width=TDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION, height=TDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION);


}
