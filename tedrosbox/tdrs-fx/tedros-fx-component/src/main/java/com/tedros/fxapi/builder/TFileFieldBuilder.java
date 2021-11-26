/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;

import com.tedros.core.context.TedrosContext;
import com.tedros.ejb.base.entity.ITFileEntity;
import com.tedros.fxapi.annotation.control.TFileField;
import com.tedros.fxapi.domain.TFileModelType;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.property.TBytesLoader;
import com.tedros.fxapi.property.TSimpleFileProperty;

import javafx.beans.property.Property;


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

	
	public com.tedros.fxapi.control.TFileField build(final Annotation annotation, final Property<String> fileNameProperty, final Property<byte[]> byteArrayProperty) throws Exception {
		TFileField tAnnotation = (TFileField) annotation;
		final com.tedros.fxapi.control.TFileField control = new com.tedros.fxapi.control.TFileField(TedrosContext.getStage());
		control.byteArrayProperty().bindBidirectional(byteArrayProperty);
		control.textProperty().bindBidirectional(fileNameProperty);
		callParser(tAnnotation, control);
		return control;
	}
	
	public com.tedros.fxapi.control.TFileField build(final Annotation annotation, final TSimpleFileProperty<?> property) throws Exception {
		TFileField tAnnotation = (TFileField) annotation;
		if(tAnnotation.propertyValueType().equals(TFileModelType.MODEL)) {
			final com.tedros.fxapi.control.TFileField control = new com.tedros.fxapi.control.TFileField(TedrosContext.getStage());
			control.byteArrayProperty().bindBidirectional(property.tBytesProperty());
			control.textProperty().bindBidirectional(property.tFileNameProperty());
			control.fileProperty().bindBidirectional(property.tFileProperty());
			callParser(tAnnotation, control);
			return control;
		}else if(tAnnotation.propertyValueType().equals(TFileModelType.ENTITY)) {
		
			if(tAnnotation.preLoadFileBytes())
				preLoadBytes(property);
			
			final com.tedros.fxapi.control.TFileField control = new com.tedros.fxapi.control.TFileField(TedrosContext.getStage());
			control.byteArrayProperty().bindBidirectional(property.tBytesProperty());
			control.textProperty().bindBidirectional(property.tFileNameProperty());
			control.fileSizeProperty().bindBidirectional(property.tFileSizeProperty());
			control.bytesEntityIdProperty().bindBidirectional(property.tBytesEntityIdProperty());
			callParser(tAnnotation, control);
			return control;
		}
		
		throw new IllegalArgumentException("The field "+super.getComponentDescriptor().getFieldDescriptor().getFieldName()+" "
				+ " of type "+property.getClass().getSimpleName()+" must set the propertyValueType in TFileField annotation with the value type of the field property.");
	}

	private void preLoadBytes(final TSimpleFileProperty<?> fileEntityProperty) {
		try {
			ITFileEntity v = (ITFileEntity) fileEntityProperty.getValue();
			if(fileEntityProperty.getValue()!=null && v.getByte()!=null && v.getByteEntity().getId()!=null)
				TBytesLoader.loadBytesFromTFileEntity(v.getByteEntity().getId(), fileEntityProperty.tBytesProperty());
		} catch (TProcessException e) {
			e.printStackTrace();
		}
	}	
}
