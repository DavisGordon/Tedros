package org.tedros.fx.annotation.scene.layout;

import org.tedros.fx.annotation.TAnnotationDefaultValue;
import org.tedros.fx.annotation.parser.ITAnnotationParser;
import org.tedros.fx.annotation.parser.TRegionParser;
import org.tedros.fx.annotation.scene.control.TInsets;
import org.tedros.fx.annotation.scene.control.TSize;

import javafx.scene.layout.Region;

/**
 * <pre>
 * A Region is an area of the screen that can contain other nodes and be styled using CSS.
 * 
 * It can have multiple backgrounds under its contents and multiple borders around its content. 
 * By default it's a rectangle with possible rounded corners, depending on borders. It can be made 
 * into any shape by specifying the shape. It is designed to support as much of the CSS3 specification 
 * for backgrounds and borders as is relevant to JavaFX. The full specification is available at css3-background.
 * 
 * By default a Region inherits the layout behavior of its superclass, Parent, which means that it 
 * will resize any resizable child nodes to their preferred size, but will not reposition them. 
 * If an application needs more specific layout behavior, then it should use one of the Region subclasses: 
 * StackPane, HBox, VBox, TilePane, FlowPane, BorderPane, GridPane, or AnchorPane.
 * 
 * To implement more custom layout, a Region subclass must override computePrefWidth, computePrefHeight, and 
 * layoutChildren. Note that layoutChildren is called automatically by the scene graph while executing a top-down 
 * layout pass and it should not be invoked directly by the region subclass.
 * 
 * Region subclasses which layout their children will position nodes by setting layoutX/layoutY and do not 
 * alter translateX/translateY, which are reserved for adjustments and animation.
 * </pre>
 * */
public @interface TRegion {
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TRegionParser.class}
	 * </pre>
	 * */
	public Class<? extends ITAnnotationParser<TRegion, Region>>[] parser() default {TRegionParser.class};
	
	/**
	* <pre>
	* {@link Region} Class
	* 
	*  Sets the value of the property snapToPixel. 
	*  
	*  Property description: 
	*  
	*  Defines whether this region rounds position/spacing and ceils size 
	*  values to pixel boundaries when laying out its children.
	* </pre>
	**/
	public boolean snapToPixel() default false;

	/**
	* <pre>
	* {@link Region} Class
	* 
	*  Sets the value of the property padding. 
	*  
	*  Property description: 
	*  
	*  The top,right,bottom,left padding around the region's content. 
	*  This space will be included in the calculation of the region's minimum and 
	*  preferred sizes. 
	*  
	*  By default padding is Insets.EMPTY and cannot be set to null.
	* </pre>
	**/
	public TInsets padding() default @TInsets();

	/**
	* <pre>
	* {@link Region} Class
	* 
	*  Sets the value of the property minWidth. 
	*  
	*  Property description: 
	*  
	*  Property for overriding the region's computed minimum width. 
	*  This should only be set if the region's internally computed minimum width doesn't 
	*  meet the application's layout needs. Defaults to the USE_COMPUTED_SIZE flag, which 
	*  means that minWidth(forHeight) will return the region's internally computed minimum 
	*  width. Setting this value to the USE_PREF_SIZE flag will cause minWidth(forHeight) to 
	*  return the region's preferred width, enabling applications to easily restrict the 
	*  resizability of the region.
	* </pre>
	**/
	public double minWidth() default TAnnotationDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION;

	/**
	* <pre>
	* {@link Region} Class
	* 
	*  Sets the value of the property minHeight. 
	*  
	*  Property description: 
	*  
	*  Property for overriding the region's computed minimum height. This should only be 
	*  set if the region's internally computed minimum height doesn't meet the application's 
	*  layout needs. Defaults to the USE_COMPUTED_SIZE flag, which means that minHeight(forWidth) 
	*  will return the region's internally computed minimum height. Setting this value to the 
	*  USE_PREF_SIZE flag will cause minHeight(forWidth) to return the region's preferred height, 
	*  enabling applications to easily restrict the resizability of the region.
	* </pre>
	**/
	public double minHeight() default TAnnotationDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION;

	/**
	* <pre>
	* {@link Region} Class
	* 
	*  Convenience method for overriding the region's computed minimum width and height. 
	*  This should only be called if the region's internally computed minimum size doesn't 
	*  meet the application's layout needs.
	*  
	*  See Also: setMinWidth(double), setMinHeight(double)
	* </pre>
	**/
	public TSize minSize() default @TSize(width=TAnnotationDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION, height=TAnnotationDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION);;

	/**
	* <pre>
	* {@link Region} Class
	* 
	*  Sets the value of the property prefWidth. 
	*  
	*  Property description: 
	*  
	*  Property for overriding the region's computed preferred width. 
	*  This should only be set if the region's internally computed preferred width doesn't 
	*  meet the application's layout needs. Defaults to the USE_COMPUTED_SIZE flag, which 
	*  means that getPrefWidth(forHeight) will return the region's internally computed preferred width.
	* </pre>
	**/
	public double prefWidth() default TAnnotationDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION;

	/**
	* <pre>
	* {@link Region} Class
	* 
	*  Sets the value of the property prefHeight. 
	*  
	*  Property description: 
	*  
	*  Property for overriding the region's computed preferred height. 
	*  This should only be set if the region's internally computed preferred height doesn't meet 
	*  the application's layout needs. Defaults to the USE_COMPUTED_SIZE flag, which means that 
	*  getPrefHeight(forWidth) will return the region's internally computed preferred width.
	* </pre>
	**/
	public double prefHeight() default TAnnotationDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION;

	/**
	* <pre>
	* {@link Region} Class
	* 
	*  Convenience method for overriding the region's computed preferred width and height. 
	*  This should only be called if the region's internally computed preferred size doesn't 
	*  meet the application's layout needs. Parameters: prefWidth - the override value for 
	*  preferred width prefHeight - the override value for preferred height 
	*  
	*  See Also: setPrefWidth(double), setPrefHeight(double)
	* </pre>
	**/
	public TSize prefSize() default @TSize(width=TAnnotationDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION, height=TAnnotationDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION);;

	/**
	* <pre>
	* {@link Region} Class
	* 
	*  Sets the value of the property maxWidth. 
	*  
	*  Property description: 
	*  
	*  Property for overriding the region's computed maximum width. This should only be set if 
	*  the region's internally computed maximum width doesn't meet the application's layout needs. 
	*  Defaults to the USE_COMPUTED_SIZE flag, which means that getMaxWidth(forHeight) will return 
	*  the region's internally computed maximum width. Setting this value to the USE_PREF_SIZE flag 
	*  will cause getMaxWidth(forHeight) to return the region's preferred width, enabling applications 
	*  to easily restrict the resizability of the region.
	* </pre>
	**/
	public double maxWidth() default TAnnotationDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION;

	/**
	* <pre>
	* {@link Region} Class
	* 
	*  Sets the value of the property maxHeight. 
	*  
	*  Property description: 
	*  
	*  Property for overriding the region's computed maximum height. This should only be set if 
	*  the region's internally computed maximum height doesn't meet the application's layout needs. 
	*  Defaults to the USE_COMPUTED_SIZE flag, which means that getMaxHeight(forWidth) will return 
	*  the region's internally computed maximum height. Setting this value to the USE_PREF_SIZE flag 
	*  will cause getMaxHeight(forWidth) to return the region's preferred height, enabling applications 
	*  to easily restrict the resizability of the region.
	* </pre>
	**/
	public double maxHeight() default TAnnotationDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION;

	/**
	* <pre>
	* {@link Region} Class
	* 
	*  Convenience method for overriding the region's computed maximum width and height. 
	*  This should only be called if the region's internally computed maximum size doesn't 
	*  meet the application's layout needs. Parameters: maxWidth - the override value for 
	*  maximum width maxHeight - the override value for maximum height 
	*  
	*  See Also: setMaxWidth(double), setMaxHeight(double)
	* </pre>
	**/
	public TSize maxSize() default @TSize(width=TAnnotationDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION, height=TAnnotationDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION);
	
	/**
	 * <pre>
	 * Force the parser execution 
	 * </pre>
	 * */
	public boolean parse();
}
