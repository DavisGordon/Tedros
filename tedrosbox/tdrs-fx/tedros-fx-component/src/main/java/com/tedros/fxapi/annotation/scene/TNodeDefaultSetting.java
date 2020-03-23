package com.tedros.fxapi.annotation.scene;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javafx.event.EventDispatcher;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.InputMethodRequests;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.RotateEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.SwipeEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.input.ZoomEvent;

import com.tedros.fxapi.annotation.TAnnotationDefaultValue;
import com.tedros.fxapi.annotation.TCursor;
import com.tedros.fxapi.annotation.TDepthTest;
import com.tedros.fxapi.annotation.TEventHandler;
import com.tedros.fxapi.annotation.effect.TBlend;
import com.tedros.fxapi.annotation.effect.TEffect;
import com.tedros.fxapi.builder.ITEventHandlerBuilder;
import com.tedros.fxapi.builder.ITNodeBuilder;
import com.tedros.fxapi.builder.NullContextMenuEventBuilder;
import com.tedros.fxapi.builder.NullDragEventBuilder;
import com.tedros.fxapi.builder.NullEventDispatcher;
import com.tedros.fxapi.builder.NullEventType;
import com.tedros.fxapi.builder.NullInputMethodEventBuilder;
import com.tedros.fxapi.builder.NullInputMethodRequests;
import com.tedros.fxapi.builder.NullKeyEventBuilder;
import com.tedros.fxapi.builder.NullMouseDragEventBuilder;
import com.tedros.fxapi.builder.NullMouseEventBuilder;
import com.tedros.fxapi.builder.NullRotateEventBuilder;
import com.tedros.fxapi.builder.NullScrollEventBuilder;
import com.tedros.fxapi.builder.NullSwipeEventBuilder;
import com.tedros.fxapi.builder.NullTouchEventBuilder;
import com.tedros.fxapi.builder.NullZoomEventBuilder;
import com.tedros.fxapi.domain.TBlendMode;
import com.tedros.fxapi.domain.TPoint3D;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface TNodeDefaultSetting {
	
	/**
	* <pre>{@link Node} property
	*  Convenience method for setting a single Object property that can be retrieved 
	*  at a later date. 
	*  
	*  This is functionally equivalent to calling the getProperties().put(Object key, Object value) method. 
	*  
	*  This can later be retrieved by calling getUserData(). 
	*  
	*  Parameters: value - The value to be stored - this can later be retrieved by calling getUserData().
	* </pre>
	**/
	public Class<?> userData() default Object.class;

	/**
	* <pre>
	*  {@link Node} property
	* 
	*  Sets the value of the property id. 
	*  
	*  Property description: 
	*  
	*  The id of this Node. This simple string identifier is useful for finding a 
	*  specific Node within the scene graph. While the id of a Node should be 
	*  unique within the scene graph, this uniqueness is not enforced. 
	*  
	*  This is analogous to the "id" attribute on an HTML element (CSS ID Specification). 
	*  
	*  For example, if a Node is given the id of "myId", then the lookup method can 
	*  be used to find this node as follows: scene.lookup("#myId");. 
	*  
	*  Default value: null
	* </pre>
	**/
	public String id() default "";

	/**
	* <pre>
	*  {@link Node} property
	*  A string representation of the CSS style associated with this specific Node. 
	*  
	*  This is analogous to the "style" attribute of an HTML element. Note that, 
	*  like the HTML style attribute, this variable contains style properties and 
	*  values and not the selector portion of a style rule. 
	*  
	*  Parameters: value - The inline CSS style to use for this Node. null is implicitly 
	*  converted to an empty String. 
	*  
	*  Default value: empty string
	* </pre>
	**/
	public String style() default "";

	/**
	* <pre>
	*  {@link Node} property
	*  Sets the value of the property visible. 
	*  
	*  Property description: 
	*  
	*  Specifies whether this Node and any subnodes should be rendered as part of the 
	*  scene graph. A node may be visible and yet not be shown in the rendered scene if, 
	*  for instance, it is off the screen or obscured by another Node. Invisible nodes 
	*  never receive mouse events or keyboard focus and never maintain keyboard focus when 
	*  they become invisible. 
	*  
	*  Default value: true
	* </pre>
	**/
	public boolean visible() default true;

	/**
	* <pre>
	*  {@link Node} property
	*  
	*  Sets the value of the property cursor. 
	*  
	*  Property description: 
	*  
	*  Defines the mouse cursor for this Node and subnodes. If null, then the cursor of the
	*  first parent node with a non-null cursor will be used. If no Node in the scene graph 
	*  defines a cursor, then the cursor of the Scene will be used. 
	*  
	*  Default value: TCursor.DEFAULT
	* </pre>
	**/
	public TCursor cursor() default TCursor.DEFAULT;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property opacity. 
	*  
	*  Property description: 
	*  
	*  Specifies how opaque (that is, solid) the Node appears. A Node with 0% opacity is fully 
	*  translucent. That is, while it is still visible and rendered, you generally won't be 
	*  able to see it. The exception to this rule is when the Node is combined with a blending 
	*  mode and blend effect in which case a translucent Node may still have an impact in rendering. 
	*  
	*  An opacity of 50% will render the node as being 50% transparent. A visible node with any 
	*  opacity setting still receives mouse events and can receive keyboard focus. 
	*  
	*  For example, if you want to have a large invisible rectangle overlay all Nodes in the scene 
	*  graph in order to intercept mouse events but not be visible to the user, you could create a 
	*  large Rectangle that had an opacity of 0%. 
	*  
	*  Opacity is specified as a value between 0 and 1. Values less than 0 are treated as 0, values 
	*  greater than 1 are treated as 1. On some platforms ImageView might not support opacity variable. 
	*  
	*  There is a known limitation of mixing opacity < 1.0 with a 3D Transform. Opacity/Blending is 
	*  essentially a 2D image operation. The result of an opacity < 1.0 set on a Group node with 3D 
	*  transformed children will cause its children to be rendered in order without Z-buffering applied 
	*  between those children. 
	*  
	*  Default value: 1.0
	* </pre>
	**/
	public double opacity() default 1.0;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property blendMode. 
	*  
	*  Property description: 
	*  
	*  The BlendMode used to blend this individual node into the scene behind it. If this node happens 
	*  to be a Group then all of the children will be composited individually into a temporary buffer 
	*  using their own blend modes and then that temporary buffer will be composited into the scene 
	*  using the specified blend mode. A value of null is treated as pass-though this means no effect 
	*  on a parent such as a Group and the equivalent of SRC_OVER for a single Node. 
	*  
	*  Default value: TBlendMode.NULL
	* </pre>
	**/
	public TBlendMode blendMode() default TBlendMode.NULL;
	
	

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property clip. 
	*  
	*  Property description: Specifies a Node to use to define the the clipping shape for this Node. 
	*  This clipping Node is not a child of this Node in the scene graph sense. Rather, it is used 
	*  to define the clip for this Node. For example, you can use an ImageView Node as a mask to 
	*  represent the Clip. Or you could use one of the geometric shape Nodes such as Rectangle or 
	*  Circle. Or you could use a Text node to represent the Clip. See the class documentation for 
	*  Node for scene graph structure restrictions on setting the clip. If these restrictions are 
	*  violated by a change to the clip variable, the change is ignored and the previous value of the 
	*  clip variable is restored. Note that this is a conditional feature. 
	*  
	*  See ConditionalFeature.SHAPE_CLIP for more information. 
	*  
	*  There is a known limitation of mixing Clip with a 3D Transform. Clipping is essentially a 2D image 
	*  operation. The result of a Clip set on a Group node with 3D transformed children will cause its 
	*  children to be rendered in order without Z-buffering applied between those children. 
	*  
	*  Default value: null
	* </pre>
	**/
	@SuppressWarnings("rawtypes")
	public Class<? extends ITNodeBuilder> clip() default ITNodeBuilder.class;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property cache. 
	*  
	*  Property description: 
	*  
	*  A performance hint to the system to indicate that this Node should be cached as a bitmap. 
	*  Rendering a bitmap representation of a node will be faster than rendering primitives in 
	*  many cases, especially in the case of primitives with effects applied (such as a blur). 
	*  
	*  However, it also increases memory usage. This hint indicates whether that trade-off 
	*  (increased memory usage for increased performance) is worthwhile. Also note that on some 
	*  platforms such as GPU accelerated platforms there is little benefit to caching Nodes as 
	*  bitmaps when blurs and other effects are used since they are very fast to render on the GPU. 
	*  
	*  The cacheHintProperty() variable provides additional options for enabling more aggressive 
	*  bitmap caching. Caching may be disabled for any node that has a 3D transform on itself, 
	*  any of its ancestors, or any of its descendants. 
	*  
	*  Default value: false
	* </pre>
	**/
	public boolean cache() default false;

	/**
	* <pre>
	* {@link Node} property
	*  
	*  Sets the value of the property cacheHint. 
	*  
	*  Property description: 
	*  
	*  Additional hint for controlling bitmap caching. Under certain circumstances, such as animating 
	*  nodes that are very expensive to render, it is desirable to be able to perform transformations 
	*  on the node without having to regenerate the cached bitmap. An option in such cases is to perform 
	*  the transforms on the cached bitmap itself. This technique can provide a dramatic improvement 
	*  to animation performance, though may also result in a reduction in visual quality. 
	*  
	*  The cacheHint variable provides a hint to the system about how and when that trade-off 
	*  (visual quality for animation performance) is acceptable. It is possible to enable the cacheHint 
	*  only at times when your node is animating. In this way, expensive nodes can appear on screen with 
	*  full visual quality, yet still animate smoothly. 
	*  
	*  Example: 
	*       expensiveNode.setCache(true);
	*       expensiveNode.setCacheHint(CacheHint.QUALITY);
	*       ...
	*       // Do an animation
	*       expensiveNode.setCacheHint(CacheHint.SPEED);
	*       new Timeline(
	*           new KeyFrame(Duration.seconds(2),
	*               new KeyValue(expensiveNode.scaleXProperty(), 2.0),
	*               new KeyValue(expensiveNode.scaleYProperty(), 2.0),
	*               new KeyValue(expensiveNode.rotateProperty(), 360),
	*               new KeyValue(expensiveNode.cacheHintProperty(), CacheHint.QUALITY)
	*           )
	*       ).play();
	*    
	*    Note that cacheHint is only a hint to the system. Depending on the details of the node or the 
	*    transform, this hint may be ignored. If Node.cache is false, cacheHint is ignored. Caching may 
	*    be disabled for any node that has a 3D transform on itself, any of its ancestors, or any of its 
	*    descendants. 
	*    
	*    Default value: CacheHint.DEFAULT
	* </pre>
	**/
	public CacheHint cacheHint() default CacheHint.DEFAULT;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property effect. 
	*  
	*  Property description: 
	*  
	*  Specifies an effect to apply to this Node. Note that this is a conditional feature. 
	*  See ConditionalFeature.EFFECT for more information. There is a known limitation of mixing Effect 
	*  with a 3D Transform. Effect is essentially a 2D image operation. The result of an Effect set on 
	*  a Group node with 3D transformed children will cause its children to be rendered in order without 
	*  Z-buffering applied between those children. 
	*  
	*  Default value: null
	* </pre>
	* @see TEffect
	**/
	public TEffect effect() default @TEffect(parse = false);
	
	/**
	 * <pre>
	 * {@link Node} property
	 * 
	 * Sets the value of the property effect with a {@link TBlend} effect. 
	 * 
	 * Default value: null
	 * </pre>
	 * @see TBlend
	 * */
	public TBlend blendEffect() default @TBlend(parse = false);

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property depthTest. 
	*  
	*  Property description: 
	*  
	*  Indicates whether depth testing is used when rendering this node. If the depthTest flag is 
	*  DepthTest.DISABLE, then depth testing is disabled for this node. If the depthTest flag is 
	*  DepthTest.ENABLE, then depth testing is enabled for this node. If the depthTest flag is 
	*  DepthTest.INHERIT, then depth testing is enabled for this node if it is enabled for the 
	*  parent node or the parent node is null. The depthTest flag is only used when the depthBuffer 
	*  flag for the Scene is true (meaning that the Scene has an associated depth buffer) Note 
	*  that this is a conditional feature. 
	*  
	*  See ConditionalFeature.SCENE3D for more information. See the constructor in Scene with depthBuffer 
	*  as one of its input arguments. 
	*  
	*  Default value: TDepthTest.INHERIT
	* </pre>
	**/
	public TDepthTest depthTest() default TDepthTest.INHERIT;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property disable. 
	*  
	*  Property description: 
	*  
	*  Sets the individual disabled state of this Node. Setting disable to true will cause this Node 
	*  and any subnodes to become disabled. This variable should be used only to set the disabled 
	*  state of a Node. For querying the disabled state of a Node, the disabled variable should instead 
	*  be used, since it is possible that a Node was disabled as a result of an ancestor being disabled 
	*  even if the individual disable state on this Node is false. 
	*  
	*  Default value: false
	* </pre>
	**/
	public boolean disable() default false;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property pickOnBounds. 
	*  
	*  Property description: 
	*  
	*  Defines how the picking computation is done for this node when triggered by a MouseEvent or a 
	*  contains function call. If pickOnBounds is true, then picking is computed by intersecting with 
	*  the bounds of this node, else picking is computed by intersecting with the geometric shape of 
	*  this node. 
	*  
	*  Default value: false
	* </pre>
	**/
	public boolean pickOnBounds() default false;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property disabled. 
	*  
	*  Property description: 
	*  
	*  Indicates whether or not this Node is disabled. A Node will become disabled if disable is set 
	*  to true on either itself or one of its ancestors in the scene graph. 
	*  
	*  A disabled Node should render itself differently to indicate its disabled state to the user. 
	*  Such disabled rendering is dependent on the implementation of the Node. The shape classes 
	*  contained in javafx.scene.shape do not implement such rendering by default, therefore applications 
	*  using shapes for handling input must implement appropriate disabled rendering themselves. 
	*  
	*  The user-interface controls defined in javafx.scene.control will implement disabled-sensitive 
	*  rendering, however. A disabled Node does not receive mouse or key events. 
	*  
	*  Default value: false
	* </pre>
	**/
	public boolean disabled() default false;
	
	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property onDragEntered. 
	*  
	*  Property description: 
	*  
	*  Defines a function to be called when drag gesture enters this Node.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<DragEvent>> onDragEntered() default NullDragEventBuilder.class;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property onDragExited. 
	*  
	*  Property description: 
	*  
	*  Defines a function to be called when drag gesture exits this Node.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<DragEvent>> onDragExited() default NullDragEventBuilder.class;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property onDragOver. 
	*  
	*  Property description: 
	*  
	*  Defines a function to be called when drag gesture progresses within this Node.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<DragEvent>> onDragOver() default NullDragEventBuilder.class;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property onDragDropped. 
	*  
	*  Property description: 
	*  
	*  Defines a function to be called when the mouse button is released on this Node 
	*  during drag and drop gesture. Transfer of data from the DragEvent's dragboard 
	*  should happen in this function.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<DragEvent>> onDragDropped() default NullDragEventBuilder.class;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property onDragDone. 
	*  
	*  Property description: 
	*  
	*  Defines a function to be called when this Node is a drag and drop gesture source 
	*  after its data has been dropped on a drop target. The transferMode of the event 
	*  shows what just happened at the drop target. If transferMode has the value MOVE, 
	*  then the source can clear out its data. Clearing the source's data gives the 
	*  appropriate appearance to a user that the data has been moved by the drag and drop 
	*  gesture. A transferMode that has the value NONE indicates that no data was transferred 
	*  during the drag and drop gesture.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<DragEvent>> onDragDone() default NullDragEventBuilder.class;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property managed. 
	*  
	*  Property description: 
	*  
	*  Defines whether or not this node's layout will be managed by it's parent. If the node 
	*  is managed, it's parent will factor the node's geometry into its own preferred size 
	*  and layoutBounds calculations and will lay it out during the scene's layout pass. 
	*  
	*  If a managed node's layoutBounds changes, it will automatically trigger relayout up 
	*  the scene-graph to the nearest layout root (which is typically the scene's root node). 
	*  
	*  If the node is unmanaged, its parent will ignore the child in both preferred size 
	*  computations and layout. Changes in layoutBounds will not trigger relayout above it. 
	*  
	*  If an unmanaged node is of type Parent, it will act as a "layout root", meaning that 
	*  calls to Parent.requestLayout() beneath it will cause only the branch rooted by the node 
	*  to be relayed out, thereby isolating layout changes to that root and below. 
	*  
	*  It's the application's responsibility to set the size and position of an unmanaged node. 
	*  
	*  By default all nodes are managed.
	* </pre>
	**/
	public boolean managed() default true;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property layoutX. 
	*  
	*  Property description: 
	*  
	*  Defines the x coordinate of the translation that is added to this Node's transform for the 
	*  purpose of layout. The value should be computed as the offset required to adjust the position 
	*  of the node from its current layoutBounds minX position (which might not be 0) to the desired 
	*  location. 
	*  
	*  For example, if textnode should be positioned at finalX	     
	*  
	*  textnode.setLayoutX(finalX - textnode.getLayoutBounds().getMinX());	 
	*  
	*  Failure to subtract layoutBounds minX may result in misplacement of the node. The relocate(x, y) 
	*  method will automatically do the correct computation and should generally be used over setting 
	*  layoutX directly. The node's final translation will be computed as layoutX + translateX, 
	*  where layoutX establishes the node's stable position and translateX optionally makes dynamic 
	*  adjustments to that position. 
	*  
	*  If the node is managed and has a Region as its parent, then the layout region will set layoutX 
	*  according to its own layout policy. 
	*  
	*  If the node is unmanaged or parented by a Group, then the application may set layoutX directly to position it.
	* </pre>
	**/
	public double layoutX() default TAnnotationDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property layoutY. 
	*  
	*  Property description: 
	*  
	*  Defines the y coordinate of the translation that is added to this Node's transform for the purpose of layout. 
	*  The value should be computed as the offset required to adjust the position of the node from its current 
	*  layoutBounds minY position (which might not be 0) to the desired location. 
	*  
	*  For example, if textnode should be positioned at finalY
	*  
	*  textnode.setLayoutY(finalY - textnode.getLayoutBounds().getMinY());
	*  
	*  Failure to subtract layoutBounds minY may result in misplacement of the node. The relocate(x, y) method will 
	*  automatically do the correct computation and should generally be used over setting layoutY directly. 
	*  The node's final translation will be computed as layoutY + translateY, where layoutY establishes the node's 
	*  stable position and translateY optionally makes dynamic adjustments to that position. 
	*  
	*  If the node is managed and has a Region as its parent, then the region will set layoutY according to its own 
	*  layout policy. 
	*  
	*  If the node is unmanaged or parented by a Group, then the application may set layoutY directly to position it.
	* </pre>
	**/
	public double layoutY() default TAnnotationDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property translateX. 
	*  
	*  Property description: 
	*  
	*  Defines the x coordinate of the translation that is added to this Node's transform. 
	*  The node's final translation will be computed as layoutX + translateX, where layoutX establishes the node's 
	*  stable position and translateX optionally makes dynamic adjustments to that position. This variable can be used 
	*  to alter the location of a node without disturbing its layoutBounds, which makes it useful for animating a node's 
	*  location. 
	*  
	*  Default value: 0
	* </pre>
	**/
	public double translateX() default 0;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property translateY. 
	*  
	*  Property description: 
	*  
	*  Defines the y coordinate of the translation that is added to this Node's transform. The node's final translation will 
	*  be computed as layoutY + translateY, where layoutY establishes the node's stable position and translateY optionally 
	*  makes dynamic adjustments to that position. This variable can be used to alter the location of a node without disturbing 
	*  its layoutBounds, which makes it useful for animating a node's location. 
	*  
	*  Default value: 0
	* </pre>
	**/
	public double translateY() default 0;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property translateZ. 
	*  
	*  Property description: 
	*  
	*  Defines the Z coordinate of the translation that is added to the transformed coordinates of 
	*  this Node. This value will be added to any translation defined by the transforms ObservableList 
	*  and layoutZ. This variable can be used to alter the location of a Node without disturbing its 
	*  layout bounds, which makes it useful for animating a node's location. Note that this is a conditional 
	*  feature. See ConditionalFeature.SCENE3D for more information. 
	*  
	*  Default value: 0
	* </pre>
	**/
	public double translateZ() default 0;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property scaleX. 
	*  
	*  Property description:
	*  
	*  Defines the factor by which coordinates are scaled about the center of the object along the 
	*  X axis of this Node. This is used to stretch or animate the node either manually or by using 
	*  an animation. This scale factor is not included in layoutBounds by default, which makes it 
	*  ideal for scaling the entire node after all effects and transforms have been taken into account. 
	*  The pivot point about which the scale occurs is the center of the untransformed layoutBounds. 
	*  
	*  Default value: 1.0
	* </pre>
	**/
	public double scaleX() default 1.0;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property scaleY. 
	*  
	*  Property description: 
	*  
	*  Defines the factor by which coordinates are scaled about the center of the object along the 
	*  Y axis of this Node. This is used to stretch or animate the node either manually or by using 
	*  an animation. This scale factor is not included in layoutBounds by default, which makes it 
	*  ideal for scaling the entire node after all effects and transforms have been taken into account. 
	*  The pivot point about which the scale occurs is the center of the untransformed layoutBounds. 
	*  
	*  Default value: 1.0
	* </pre>
	**/
	public double scaleY() default 1.0;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property scaleZ. 
	*  
	*  Property description: 
	*  
	*  Defines the factor by which coordinates are scaled about the center of the object along the 
	*  Z axis of this Node. This is used to stretch or animate the node either manually or by using 
	*  an animation. This scale factor is not included in layoutBounds by default, which makes it 
	*  ideal for scaling the entire node after all effects and transforms have been taken into account. 
	*  The pivot point about which the scale occurs is the center of the rectangular bounds formed by 
	*  taking boundsInLocal and applying all the transforms in the transforms ObservableList. 
	*  
	*  Note that this is a conditional feature. 
	*  
	*  See ConditionalFeature.SCENE3D for more information. 
	*  
	*  Default value: 1.0
	* </pre>
	**/
	public double scaleZ() default 1.0;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property rotate. 
	*  
	*  Property description: 
	*  
	*  Defines the angle of rotation about the Node's center, measured in degrees. 
	*  This is used to rotate the Node. This rotation factor is not included in layoutBounds by default, 
	*  which makes it ideal for rotating the entire node after all effects and transforms have been taken 
	*  into account. The pivot point about which the rotation occurs is the center of the untransformed 
	*  layoutBounds. Note that because the pivot point is computed as the center of this Node's layout 
	*  bounds, any change to the layout bounds will cause the pivot point to change, which can move the object. 
	*  
	*  For a leaf node, any change to the geometry will cause the layout bounds to change. 
	*  For a group node, any change to any of its children, including a change in a child's geometry, clip, 
	*  effect, position, orientation, or scale, will cause the group's layout bounds to change. 
	*  If this movement of the pivot point is not desired, applications should instead use the Node's transforms 
	*  ObservableList, and add a Rotate transform, which has a user-specifiable pivot point. 
	*  
	*  Default value: 0.0
	* </pre>
	**/
	public double rotate() default 0.0;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property rotationAxis. 
	*  
	*  Property description: 
	*  
	*  Defines the axis of rotation of this Node. Note that this is a conditional feature. 
	*  
	*  See ConditionalFeature.SCENE3D for more information. 
	*  
	*  Default value: TPoint3D.Z_AXIS (Equal Rotate.Z_AXIS)
	* </pre>
	**/
	public TPoint3D rotationAxis() default TPoint3D.Z_AXIS;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property mouseTransparent. 
	*  
	*  Property description: 
	*  
	*  If true, this node (together with all its children) is completely transparent to mouse events. 
	*  When choosing target for mouse event, nodes with mouseTransparent set to true and their subtrees 
	*  won't be taken into account.
	* </pre>
	**/
	public boolean mouseTransparent() default false;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property hover. 
	*  
	*  Property description: 
	*  
	*  Whether or not this Node is being hovered over. Typically this is due to the mouse 
	*  being over the node, though it could be due to a pen hovering on a graphics tablet 
	*  or other form of input. Note that current implementation of hover relies on mouse 
	*  enter and exit events to determine whether this Node is in the hover state; this means 
	*  that this feature is currently supported only on systems that have a mouse. 
	*  
	*  Future implementations may provide alternative means of supporting hover. 
	*  
	*  Default value: false
	* </pre>
	**/
	public boolean hover() default false;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property pressed. 
	*  
	*  Property description: 
	*  
	*  Whether or not the Node is pressed. Typically this is true when the primary mouse 
	*  button is down, though subclasses may define other mouse button state or key state 
	*  to cause the node to be "pressed". 
	*  
	*  Default value: false
	* </pre>
	**/
	public boolean pressed() default false;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property onContextMenuRequested. 
	*  
	*  Property description: 
	*  
	*  Defines a function to be called when a context menu has been requested on this Node.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<ContextMenuEvent>> onContextMenuRequested() default NullContextMenuEventBuilder.class;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property onMouseClicked. 
	*  
	*  Property description: 
	*  
	*  Defines a function to be called when a mouse button has been clicked (pressed and released) on this Node.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<MouseEvent>> onMouseClicked() default NullMouseEventBuilder.class;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property onMouseDragged. 
	*  
	*  Property description: 
	*  
	*  Defines a function to be called when a mouse button is pressed on this Node and then dragged.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<MouseEvent>> onMouseDragged() default NullMouseEventBuilder.class;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property onMouseEntered. 
	*  
	*  Property description: 
	*  
	*  Defines a function to be called when the mouse enters this Node.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<MouseEvent>> onMouseEntered() default NullMouseEventBuilder.class;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property onMouseExited. 
	*  
	*  Property description: 
	*  
	*  Defines a function to be called when the mouse exits this Node.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<MouseEvent>> onMouseExited() default NullMouseEventBuilder.class;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property onMouseMoved. 
	*  
	*  Property description: 
	*  
	*  Defines a function to be called when mouse cursor moves within this Node but no buttons have been pushed.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<MouseEvent>> onMouseMoved() default NullMouseEventBuilder.class;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property onMousePressed. 
	*  
	*  Property description: 
	*  
	*  Defines a function to be called when a mouse button has been pressed on this Node.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<MouseEvent>> onMousePressed() default NullMouseEventBuilder.class;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property onMouseReleased.
	*   
	*  Property description: 
	*  
	*  Defines a function to be called when a mouse button has been released on this Node.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<MouseEvent>> onMouseReleased() default NullMouseEventBuilder.class;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property onDragDetected. 
	*  
	*  Property description: 
	*  
	*  Defines a function to be called when drag gesture has been detected. 
	*  This is the right place to start drag and drop operation.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<MouseEvent>> onDragDetected() default NullMouseEventBuilder.class;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property onMouseDragOver. 
	*  
	*  Property description: 
	*  
	*  Defines a function to be called when a full press-drag-release gesture progresses within this Node.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<MouseDragEvent>> onMouseDragOver() default NullMouseDragEventBuilder.class;

	/**
	* <pre>{@link Node} property
	*  Sets the value of the property onMouseDragReleased. Property description: Defines a function to be called when a full press-drag-release gesture ends (by releasing mouse button) within this Node.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<MouseDragEvent>> onMouseDragReleased() default NullMouseDragEventBuilder.class;

	/**
	* <pre>{@link Node} property
	*  Sets the value of the property onMouseDragEntered. Property description: Defines a function to be called when a full press-drag-release gesture enters this Node.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<MouseDragEvent>> onMouseDragEntered() default NullMouseDragEventBuilder.class;

	/**
	* <pre>{@link Node} property
	*  Sets the value of the property onMouseDragExited. Property description: Defines a function to be called when a full press-drag-release gesture leaves this Node.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<MouseDragEvent>> onMouseDragExited() default NullMouseDragEventBuilder.class;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property onScrollStarted. 
	*  
	*  Property description: 
	*  
	*  Defines a function to be called when a scrolling gesture is detected.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<ScrollEvent>> onScrollStarted() default NullScrollEventBuilder.class;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property onScroll. 
	*  
	*  Property description: 
	*  
	*  Defines a function to be called when user performs a scrolling action.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<ScrollEvent>> onScroll() default NullScrollEventBuilder.class;

	/**
	* <pre>{@link Node} property
	* 
	*  Sets the value of the property onScrollFinished. 
	*  
	*  Property description: 
	*  
	*  Defines a function to be called when a scrolling gesture ends.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<ScrollEvent>> onScrollFinished() default NullScrollEventBuilder.class;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property onRotationStarted. 
	*  
	*  Property description: 
	*  
	*  Defines a function to be called when a rotation gesture is detected.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<RotateEvent>> onRotationStarted() default NullRotateEventBuilder.class;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property onRotate. 
	*  
	*  Property description: 
	*  
	*  Defines a function to be called when user performs a rotation action.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<RotateEvent>> onRotate() default NullRotateEventBuilder.class;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property onRotationFinished. 
	*  
	*  Property description: 
	*  
	*  Defines a function to be called when a rotation gesture ends.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<RotateEvent>> onRotationFinished() default NullRotateEventBuilder.class;

	/**
	* <pre>{@link Node} property
	*  Sets the value of the property onZoomStarted. Property description: Defines a function to be called when a zooming gesture is detected.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<ZoomEvent>> onZoomStarted() default NullZoomEventBuilder.class;

	/**
	* <pre>{@link Node} property
	*  Sets the value of the property onZoom. Property description: Defines a function to be called when user performs a zooming action.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<ZoomEvent>> onZoom() default NullZoomEventBuilder.class;

	/**
	* <pre>{@link Node} property
	*  Sets the value of the property onZoomFinished. Property description: Defines a function to be called when a zooming gesture ends.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<ZoomEvent>> onZoomFinished() default NullZoomEventBuilder.class;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property onSwipeUp. 
	*  
	*  Property description: 
	*  
	*  Defines a function to be called when an upward swipe gesture centered over this node happens.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<SwipeEvent>> onSwipeUp() default NullSwipeEventBuilder.class;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property onSwipeDown. 
	*  
	*  Property description: 
	*  
	*  Defines a function to be called when a downward swipe gesture centered over this node happens.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<SwipeEvent>> onSwipeDown() default NullSwipeEventBuilder.class;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property onSwipeLeft. 
	*  
	*  Property description: 
	*  
	*  Defines a function to be called when a leftward swipe gesture centered over this node happens.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<SwipeEvent>> onSwipeLeft() default NullSwipeEventBuilder.class;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property onSwipeRight. 
	*  
	*  Property description: 
	*  
	*  Defines a function to be called when an rightward swipe gesture centered over this node happens.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<SwipeEvent>> onSwipeRight() default NullSwipeEventBuilder.class;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property onTouchPressed. 
	*  
	*  Property description: 
	*  
	*  Defines a function to be called when a new touch point is pressed.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<TouchEvent>> onTouchPressed() default NullTouchEventBuilder.class;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property onTouchMoved. 
	*  
	*  Property description: 
	*  
	*  Defines a function to be called when a touch point is moved.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<TouchEvent>> onTouchMoved() default NullTouchEventBuilder.class;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property onTouchReleased. 
	*  
	*  Property description: 
	*  
	*  Defines a function to be called when a touch point is released.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<TouchEvent>> onTouchReleased() default NullTouchEventBuilder.class;

	/**
	* <pre>
	* {@link Node} property
	*  
	*  Sets the value of the property onTouchStationary. 
	*  
	*  Property description: 
	*  
	*  Defines a function to be called when a touch point stays pressed and still.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<TouchEvent>> onTouchStationary() default NullTouchEventBuilder.class;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property onKeyPressed. 
	*  
	*  Property description: 
	*  
	*  Defines a function to be called when this Node or its 
	*  child Node has input focus and a key has been pressed.
	*   
	*  The function is called only if the event hasn't been 
	*  already consumed during its capturing or bubbling phase.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<KeyEvent>> onKeyPressed() default NullKeyEventBuilder.class;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property onKeyReleased. 
	*  
	*  Property description: 
	*  
	*  Defines a function to be called when this Node or its 
	*  child Node has input focus and a key has been released. 
	*  
	*  The function is called only if the event hasn't been 
	*  already consumed during its capturing or bubbling phase.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<KeyEvent>> onKeyReleased() default NullKeyEventBuilder.class;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property onKeyTyped. 
	*  
	*  Property description: 
	*  
	*  Defines a function to be called when this Node or its 
	*  child Node has input focus and a key has been typed. 
	*  
	*  The function is called only if the event hasn't been 
	*  already consumed during its capturing or bubbling phase.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<KeyEvent>> onKeyTyped() default NullKeyEventBuilder.class;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property onInputMethodTextChanged. 
	*  
	*  Property description: 
	*  
	*  Defines a function to be called when this Node has input 
	*  focus and the input method text has changed. 
	*  
	*  If this function is not defined in this Node, then it 
	*  receives the result string of the input method composition as 
	*  a series of onKeyTyped function calls. 
	*  
	*  When the Node loses the input focus, the JavaFX runtime 
	*  automatically commits the existing composed text if any.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<InputMethodEvent>> onInputMethodTextChanged() default NullInputMethodEventBuilder.class;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property inputMethodRequests. 
	*  
	*  Property description: 
	*  
	*  Property holding InputMethodRequests.
	*  
	*  InputMethodRequests defines the requests that a text editing node 
	*  has to handle in order to work with input methods. The node can 
	*  implement this interface itself or use a separate object that 
	*  implements it. 
	*  
	*  The object implementing this interface must be returned from the 
	*  node's getInputMethodRequests method.
	* </pre>
	**/
	public Class<? extends InputMethodRequests> inputMethodRequests() default NullInputMethodRequests.class;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property focused. 
	*  
	*  Property description: 
	*  
	*  Indicates whether this Node currently has the input focus. 
	*  To have the input focus, a node must be the Scene's focus owner, 
	*  and the scene must be in a Stage that is visible and active. 
	*  
	*  See requestFocus() for more information. 
	*  
	*  Default value: false
	* </pre>
	**/
	public boolean focused() default false;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property focusTraversable. 
	*  
	*  Property description: 
	*  
	*  Specifies whether this Node should be a part of focus traversal cycle. 
	*  When this property is true focus can be moved to this Node and from this 
	*  Node using regular focus traversal keys. 
	*  
	*  On a desktop such keys are usually TAB for moving focus forward and SHIFT+TAB 
	*  for moving focus backward. 
	*  
	*  When a Scene is created, the system gives focus to a Node whose focusTraversable 
	*  variable is true and that is eligible to receive the focus, unless the focus had 
	*  been set explicitly via a call to requestFocus(). 
	*  
	*  Default value: false
	* </pre>
	**/
	public boolean focusTraversable() default false;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the value of the property eventDispatcher. 
	*  
	*  Property description: 
	*  
	*  Specifies the event dispatcher for this node. 
	*  The default event dispatcher sends the received events to the registered event 
	*  handlers and filters. When replacing the value with a new EventDispatcher, 
	*  the new dispatcher should forward events to the replaced dispatcher to maintain 
	*  the node's default event handling behavior.
	* </pre>
	**/
	public Class<? extends EventDispatcher> eventDispatcher() default NullEventDispatcher.class;

	/**
	* <pre>
	* {@link Node} property
	* 
	*  Sets the handler to use for this event type.
	*  There can only be one such handler specified at a time. 
	*  
	*  This handler is guaranteed to be called first. This is used for registering the 
	*  user-defined onFoo event handlers.
	*  
	* </pre>
	**/
	public TEventHandler eventHandler() default @TEventHandler(eventType = NullEventType.class, parse = false);
	
}
