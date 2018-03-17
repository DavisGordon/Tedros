/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;

import javafx.beans.property.Property;

import com.tedros.core.context.TedrosContext;
import com.tedros.fxapi.annotation.control.TFileField;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.property.TSimpleFileEntityProperty;
import com.tedros.fxapi.property.TSimpleFileModelProperty;
import com.tedros.fxapi.property.TBytesLoader;


/**
 * <pre>
 * The {@link com.tedros.fxapi.control.TFileField} builder.
 *</pre>
 *
 * @author Davis Gordon
 *
 */
public final class TFileFieldBuilder extends TBuilder 
implements ITFileBuilder<com.tedros.fxapi.control.TFileField> {

	private static TFileFieldBuilder instance;
	
	private TFileFieldBuilder(){
		
	}
	
	public static  TFileFieldBuilder getInstance(){
		if(instance==null)
			instance = new TFileFieldBuilder();
		return instance;	
	}
	
	public com.tedros.fxapi.control.TFileField build(final Annotation annotation, final Property<String> fileNameProperty, final Property<byte[]> byteArrayProperty) throws Exception {
		TFileField tAnnotation = (TFileField) annotation;
		final com.tedros.fxapi.control.TFileField control = new com.tedros.fxapi.control.TFileField(TedrosContext.getStage());
		control.byteArrayProperty().bindBidirectional(byteArrayProperty);
		control.textProperty().bindBidirectional(fileNameProperty);
		callParser(tAnnotation, control);
		return control;
	}
	
	public com.tedros.fxapi.control.TFileField build(final Annotation annotation, final TSimpleFileModelProperty<?> tSimpleFileModelProperty) throws Exception {
		TFileField tAnnotation = (TFileField) annotation;
		final com.tedros.fxapi.control.TFileField control = new com.tedros.fxapi.control.TFileField(TedrosContext.getStage());
		control.byteArrayProperty().bindBidirectional(tSimpleFileModelProperty.bytesProperty());
		control.textProperty().bindBidirectional(tSimpleFileModelProperty.fileNameProperty());
		control.fileProperty().bindBidirectional(tSimpleFileModelProperty.fileProperty());
		callParser(tAnnotation, control);
		return control;
	}
	
	public com.tedros.fxapi.control.TFileField build(final Annotation annotation, final TSimpleFileEntityProperty<?> tSimpleFileEntityProperty) throws Exception {
		
		TFileField tAnnotation = (TFileField) annotation;
		
		if(tAnnotation.preLoadFileBytes())
			preLoadBytes(tSimpleFileEntityProperty);
		
		final com.tedros.fxapi.control.TFileField control = new com.tedros.fxapi.control.TFileField(TedrosContext.getStage());
		control.byteArrayProperty().bindBidirectional(tSimpleFileEntityProperty.bytesProperty());
		control.textProperty().bindBidirectional(tSimpleFileEntityProperty.fileNameProperty());
		control.fileSizeProperty().bindBidirectional(tSimpleFileEntityProperty.fileSizeProperty());
		control.bytesEntityIdProperty().bindBidirectional(tSimpleFileEntityProperty.bytesEntityIdProperty());
		callParser(tAnnotation, control);
		return control;
	}

	private void preLoadBytes(final TSimpleFileEntityProperty<?> fileEntityProperty) {
		try {
			TBytesLoader.loadBytesFromTFileEntity(fileEntityProperty.getValue().getByteEntity().getId(), fileEntityProperty.bytesProperty());
		} catch (TProcessException e) {
			e.printStackTrace();
		}
	}	
}
