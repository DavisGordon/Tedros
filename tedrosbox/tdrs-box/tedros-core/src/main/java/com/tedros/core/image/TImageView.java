/**
 * 
 */
package com.tedros.core.image;

import java.io.InputStream;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Davis Gordon
 *
 */
public abstract class TImageView extends ImageView {

	public abstract String getImagePathName();
	
	public TImageView() {
		if(getImagePathName()==null)
			throw new NullPointerException("The method getImagePathName() in "+this.getClass().getName()+" returned a null value.");
			
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(getImagePathName());
		setImage(new Image(is)); 
	}
	
}
