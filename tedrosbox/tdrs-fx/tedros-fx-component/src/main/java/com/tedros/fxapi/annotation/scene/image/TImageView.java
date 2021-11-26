/**
 * 
 */
package com.tedros.fxapi.annotation.scene.image;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.tedros.fxapi.annotation.parser.ITAnnotationParser;
import com.tedros.fxapi.annotation.parser.TImageViewParser;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.builder.ITControlBuilder;
import com.tedros.fxapi.builder.ITFieldBuilder;
import com.tedros.fxapi.builder.ITGenericBuilder;
import com.tedros.fxapi.builder.NullRectangle2DBuilder;
import com.tedros.fxapi.builder.TImageViewBuilder;
import com.tedros.fxapi.property.TSimpleFileProperty;

import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.ImageView;


/**
 * <pre>
 * Build an ImageView from an url or entity.
 * If a change is made in the property value with a new image file
 * the ImageView is updated with this new image.
 * 
 * Example: 
 * <b>Loading an image from an entity:</b>
 * <i>@</i>TImageView(fitWidth=130, preserveRatio=true)
 * private TSimpleFileProperty&ltTFileEntity&gt entityFileImage;
 * 
 * <b>Loading an image from the web:</b>
 * <i>@</i>TImageView(image=<i>@</i>TImage(url="https://www.tedrosbox.com/images/logo.png"))
 * private TSimpleFileProperty&ltTFileEntity&gt webImage;
 * 
 * <b>Loading an image from the file system:</b>
 * <i>@</i>TImageView(image=<i>@</i>TImage(url="file:c://usr/logo.png"))
 * private TSimpleFileProperty&ltTFileEntity&gt fileSystemImage;
 * </pre>
 * @author Davis Gordon
 * 
 * @see {@link TImage}
 * @see {@link TSimpleFileProperty}
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface TImageView {
	
	/**
	 *<pre>
	 * The builder of type {@link ITControlBuilder} for this component.
	 * 
	 *  Default value: {@link TImageViewBuilder}
	 *</pre> 
	 * */
	public Class<? extends ITFieldBuilder> builder() default TImageViewBuilder.class;
	
	/**
	* <pre>
	* The parser class for this annotation
	* 
	* Default value: {TImageViewParser.class}
	* </pre>
	* */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {TImageViewParser.class};
	
	/**
	 * <pre>
	 * The {@link Node} properties.
	 * </pre>
	 * */
	public TNode node() default @TNode(parse = false);
	
	
	/**
	* <pre>
	* {@link ImageView} Class
	* 
	*  Sets the value of the property image. 
	*  Property description: The Image to be painted by this ImageView. 
	*  Default value: null
	* </pre>
	**/
	public TImage image() default @TImage();

	/**
	* <pre>
	* {@link ImageView} Class
	* 
	*  Sets the value of the property x. 
	*  Property description: The current x coordinate of the ImageView origin.
	*   Default value: 0
	* </pre>
	**/
	public double x() default 0;

	/**
	* <pre>
	* {@link ImageView} Class
	* 
	*  Sets the value of the property y. 
	*  Property description: The current y coordinate of the ImageView origin. 
	*  Default value: 0
	* </pre>
	**/
	public double y() default 0;

	/**
	* <pre>
	* {@link ImageView} Class
	* 
	*  Sets the value of the property fitWidth. 
	*  Property description: The width of the bounding box within which the source 
	*  image is resized as necessary to fit. 
	*  If set to a value <= 0, then the intrinsic width of the image will be used as the fitWidth. 
	*  See preserveRatio for information on interaction between image view's fitWidth, fitHeight 
	*  and preserveRatio attributes. 
	*  Default value: 0
	* </pre>
	**/
	public double fitWidth() default 0;

	/**
	* <pre>
	* {@link ImageView} Class
	* 
	*  Sets the value of the property fitHeight. 
	*  Property description: The height of the bounding box within which the source 
	*  image is resized as necessary to fit. If set to a value <= 0, then the intrinsic 
	*  height of the image will be used as the fitHeight. See preserveRatio for information 
	*  on interaction between image view's fitWidth, fitHeight and preserveRatio attributes. 
	*  Default value: 0
	* </pre>
	**/
	public double fitHeight() default 0;

	/**
	* <pre>
	* {@link ImageView} Class
	* 
	*  Sets the value of the property preserveRatio. 
	*  Property description: Indicates whether to preserve the aspect ratio of the source 
	*  image when scaling to fit the image within the fitting bounding box. 
	*  If set to true, it affects the dimensions of this ImageView in the following way 
	*  * If only fitWidth is set, height is scaled to preserve ratio If only fitHeight is set, 
	*  width is scaled to preserve ratio If both are set, they both may be scaled to get the 
	*  best fit in a width by height rectangle while preserving the original aspect ratio 
	*  If unset or set to false, it affects the dimensions of this ImageView in the following way 
	*  * If only fitWidth is set, image's view width is scaled to match and height is unchanged; 
	*  If only fitHeight is set, image's view height is scaled to match and height is unchanged; 
	*  If both are set, the image view is scaled to match both. Note that the dimensions of this 
	*  node as reported by the node's bounds will be equal to the size of the scaled image and is 
	*  guaranteed to be contained within fitWidth x fitHeight bonding box. 
	*  Default value: false
	* </pre>
	**/
	public boolean preserveRatio() default false;

	/**
	* <pre>
	* {@link ImageView} Class
	* 
	*  Sets the value of the property smooth. 
	*  Property description: Indicates whether to use a better quality filtering algorithm or a faster one 
	*  when transforming or scaling the source image to fit within the bounding box provided by fitWidth and fitHeight. 
	*  If set to true a better quality filtering will be used, if set to false a faster but lesser quality filtering will be used. 
	*  The default value depends on platform configuration. 
	*  Default value: false
	* </pre>
	**/
	public boolean smooth() default false;

	/**
	* <pre>
	* {@link ImageView} Class
	* 
	*  Sets the value of the property viewport. 
	*  
	*  Property description: The rectangular viewport into the image. 
	*  The viewport is specified in the coordinates of the image, prior to scaling or any other transformations. 
	*  If viewport is null, the entire image is displayed. 
	*  If viewport is non-null, only the portion of the image which falls within the viewport will be displayed. 
	*  If the image does not fully cover the viewport then any remaining area of the viewport will be empty. 
	*  Default value: null
	* </pre>
	**/
	public Class<? extends ITGenericBuilder<Rectangle2D>> viewport() default NullRectangle2DBuilder.class;



}
