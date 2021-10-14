/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.util.UUID;

import com.tedros.core.context.TedrosContext;
import com.tedros.ejb.base.entity.ITFileEntity;
import com.tedros.fxapi.annotation.scene.image.TImage;
import com.tedros.fxapi.annotation.scene.image.TImageView;
import com.tedros.fxapi.form.ITForm;
import com.tedros.fxapi.property.TSimpleFileEntityProperty;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * The ImageView builder.
 *
 * @author Davis Gordon
 *
 */
public class TImageViewBuilder 
extends TBuilder 
implements ITControlBuilder<ImageView, TSimpleFileEntityProperty<ITFileEntity>>, ITReaderBuilder<ImageView, TSimpleFileEntityProperty<ITFileEntity>> {
	
	@Override
	public ImageView build(Annotation annotation, final TSimpleFileEntityProperty<ITFileEntity> property) throws Exception {
		final ImageView control = new ImageView();
		TImageView ann = (TImageView) annotation;
		TImage a = ann.image();
		Image i;
		
		byte[] bArray = property.bytesProperty().getValue();
		
		if(bArray!=null){
			ByteArrayInputStream bis = new ByteArrayInputStream(bArray);
			i = new Image(bis, a.requestedWidth(), a.requestedHeight(), a.preserveRatio(), a.smooth());
			bis.close();
		}else{
			if("default-image.jpg".equals(a.url())) {
				InputStream is = TedrosContext.getImageInputStream(a.url());
				i = new Image(is, a.requestedWidth(), a.requestedHeight(), a.preserveRatio(), a.smooth());
				is.close();
			}else{
				i = new Image(a.url(), a.requestedWidth(), a.requestedHeight(), a.preserveRatio(), a.smooth(), a.backgroundLoading());
			}
		}
		
		SimpleObjectProperty<Image> observable = new SimpleObjectProperty<>();
		ChangeListener<byte[]> chl = (x, y, z)->{
			Image img;
			if(z!=null) {
				ByteArrayInputStream bis = new ByteArrayInputStream(z);
				img = new Image(bis, a.requestedWidth(), a.requestedHeight(), a.preserveRatio(), a.smooth());
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else {
				if("default-image.jpg".equals(a.url())) {
					InputStream is2 = TedrosContext.getImageInputStream(a.url());
					img = new Image(is2, a.requestedWidth(), a.requestedHeight(), a.preserveRatio(), a.smooth());
					try {
						is2.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else{
					img = new Image(a.url(), a.requestedWidth(), a.requestedHeight(), a.preserveRatio(), a.smooth(), a.backgroundLoading());
				}
			}
			observable.setValue(img);
		};
		
		ITForm form = super.getComponentDescriptor().getForm();
		String field = super.getComponentDescriptor().getFieldDescriptor().getFieldName();
		
		form.gettObjectRepository()
			.add(field+"_listener_"+UUID.randomUUID().toString(), chl);
		form.gettObjectRepository()
			.add(field+"_observable_"+UUID.randomUUID().toString(), observable);
		
		property.bytesProperty().addListener(new WeakChangeListener<>(chl));
	
		control.imageProperty().bind(observable);
		observable.setValue(i);
		
		callParser(ann, control);
		
		return control;
	}	
}