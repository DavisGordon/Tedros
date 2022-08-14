/**
 * 
 */
package org.tedros.fx.annotation.scene.image;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;


/**
 * Specify an url to load an image
 * 
 * @author Davis Gordon
 */
@Retention(RUNTIME)
@Target(ANNOTATION_TYPE)
public @interface TImage {
	
	/**
	 * the string representing the URL to use in fetching the pixel data.
	 * If a non value is specified a default image is loaded.
	 * Default value: default-image.jpg
	 * */
	String url() default "default-image.jpg"; 
	
	/**
	 * the image's bounding box width
	 * */
	double requestedWidth() default -1; 

	/**
	 * the image's bounding box height
	 * */
	double requestedHeight() default -1; 

	/**
	 * indicates whether to preserve the aspect ratio of the original image 
	 * when scaling to fit the image within the specified bounding box
	 * */
	boolean preserveRatio() default false; 

	/**
	 *  indicates whether to use a better quality filtering algorithm or 
	 *  a faster one when scaling this image to fit within the specified bounding box
	 * */
	boolean smooth() default true; 

	/**
	 * indicates whether the image is being loaded in the background
	 * */
	boolean backgroundLoading() default false;

}
