/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;

import org.tedros.core.context.TedrosContext;
import org.tedros.fx.annotation.control.TFileField;
import org.tedros.fx.domain.TFileModelType;
import org.tedros.fx.exception.TProcessException;
import org.tedros.fx.property.TBytesLoader;
import org.tedros.fx.property.TSimpleFileProperty;
import org.tedros.server.entity.ITFileEntity;
import org.tedros.util.TLoggerUtil;

import javafx.beans.property.Property;


/**
 * <pre>
 * The {@link org.tedros.fx.control.TFileField} builder.
 *</pre>
 *
 * @author Davis Gordon
 *
 */
public final class TFileFieldBuilder extends TBuilder 
implements ITFileBuilder<org.tedros.fx.control.TFileField> {

	
	public org.tedros.fx.control.TFileField build(final Annotation annotation, final Property<String> fileNameProperty, final Property<byte[]> byteArrayProperty) throws Exception {
		TFileField tAnnotation = (TFileField) annotation;
		final org.tedros.fx.control.TFileField control = new org.tedros.fx.control.TFileField(TedrosContext.getStage());
		control.byteArrayProperty().bindBidirectional(byteArrayProperty);
		control.textProperty().bindBidirectional(fileNameProperty);
		callParser(tAnnotation, control);
		return control;
	}
	
	public org.tedros.fx.control.TFileField build(final Annotation annotation, final TSimpleFileProperty<?> property) throws Exception {
		TFileField tAnnotation = (TFileField) annotation;
		if(tAnnotation.propertyValueType().equals(TFileModelType.MODEL)) {
			final org.tedros.fx.control.TFileField control = new org.tedros.fx.control.TFileField(TedrosContext.getStage());
			control.byteArrayProperty().bindBidirectional(property.tBytesProperty());
			control.textProperty().bindBidirectional(property.tFileNameProperty());
			control.fileProperty().bindBidirectional(property.tFileProperty());
			callParser(tAnnotation, control);
			return control;
		}else if(tAnnotation.propertyValueType().equals(TFileModelType.ENTITY)) {
		
			if(tAnnotation.preLoadFileBytes())
				preLoadBytes(property);
			
			final org.tedros.fx.control.TFileField control = new org.tedros.fx.control.TFileField(TedrosContext.getStage());
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
			TLoggerUtil.error(getClass(), e.getMessage(), e);
		}
	}	
}
