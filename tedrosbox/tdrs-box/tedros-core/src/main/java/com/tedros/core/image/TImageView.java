/**
 * 
 */
package com.tedros.core.image;

import java.io.InputStream;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Expose an image 
 * 
 * @author Davis Gordon
 */
public abstract class TImageView extends ImageView {

	/**
	 * The path of the image
	 * */
	public abstract String getImagePathName();
	
	public TImageView() {
		if(getImagePathName()==null)
			throw new NullPointerException("The method getImagePathName() in "+this.getClass().getName()+" returned a null value.");
			
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(getImagePathName());
		setImage(new Image(is)); 
	}
	
}
