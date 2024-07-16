/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 18/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

import org.tedros.fx.annotation.reader.TFileReader;
import org.tedros.fx.converter.TConverter;
import org.tedros.fx.exception.TProcessException;
import org.tedros.fx.property.TBytesLoader;
import org.tedros.fx.property.TSimpleFileProperty;
import org.tedros.fx.reader.TByteArrayReader;
import org.tedros.server.entity.ITFileEntity;
import org.tedros.util.TLoggerUtil;

import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public final class TFileReaderBuilder 
extends TBuilder 
implements ITReaderBuilder<Node, TSimpleFileProperty>{

		
	@Override
	public Node build(Annotation annotation, TSimpleFileProperty property) throws Exception {
			
		TFileReader tAnnotation = (TFileReader) annotation;
		Node reader = generateFileReader(tAnnotation, property);
		callParser(tAnnotation, reader);
		return reader;
	}
	
	@SuppressWarnings("unchecked")
	private Node generateFileReader(TFileReader tAnnotation, Property<?> property) {
		
		if(tAnnotation.converter().type()!=TConverter.class){
			Class clazz = tAnnotation.converter().type();
			TConverter converter;
			try {
				converter = (TConverter) clazz.getConstructor(Object.class).newInstance(property);
				Node node = (Node) converter.getOut();
				return node;
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				
				TLoggerUtil.error(getClass(), e.getMessage(), e);
				
			}
		}else{
			final TSimpleFileProperty<?> fileProperty = (TSimpleFileProperty) property;
			try {
				final TByteArrayReader reader = new TByteArrayReader();
				reader.setShowImage(true);
				if(fileProperty.tBytesProperty().getValue()==null){
					fileProperty.tBytesProperty().addListener(new ChangeListener<byte[]>() {
						@Override
						public void changed(ObservableValue<? extends byte[]> arg0, byte[] arg1, byte[] arg2) {
							if(arg2!=null){
								reader.fileNameProperty().bindBidirectional(fileProperty.tFileNameProperty());
								reader.valueProperty().bindBidirectional(fileProperty.tBytesProperty());
							}
						}
					});
					TBytesLoader.loadBytesFromTFileEntity(((ITFileEntity)fileProperty.getValue()).getByteEntity().getId(), fileProperty.tBytesProperty());
				}else{
					reader.fileNameProperty().bindBidirectional(fileProperty.tFileNameProperty());
					reader.valueProperty().bindBidirectional(fileProperty.tBytesProperty());
				}
				return reader;
			} catch (TProcessException e) {
				TLoggerUtil.error(getClass(), e.getMessage(), e);
			}
			
		}
		
		return null;
	}

	
	
}
