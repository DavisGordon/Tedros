/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 26/03/2014
 */
package com.tedros.fxapi.reader;

import java.io.ByteArrayInputStream;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TImageReader extends BorderPane {
	
	private ImageView imageView;
	
	public TImageReader() {
		setCenter(getImageView());
		setPadding(new Insets(5,10,5,10));
	}
	
	public void setImage(byte[] imageByteArray){
		getImageView().setImage(new Image(new ByteArrayInputStream(imageByteArray)));
	}
	
	public Image getImage(){
		return getImageView().getImage();
	}
	
	public ImageView getImageView() {
		if(imageView==null)
			imageView = new ImageView();
		return imageView;
	}
	
	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}
	

}
