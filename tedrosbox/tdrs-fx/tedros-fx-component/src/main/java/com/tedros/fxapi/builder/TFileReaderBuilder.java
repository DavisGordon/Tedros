/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 18/01/2014
 */
package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;

import com.tedros.fxapi.annotation.reader.TFileReader;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.form.TConverter;
import com.tedros.fxapi.property.TBytesLoader;
import com.tedros.fxapi.property.TSimpleFileEntityProperty;
import com.tedros.fxapi.reader.TByteArrayReader;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public final class TFileReaderBuilder 
extends TBuilder 
implements ITReaderBuilder<Node, TSimpleFileEntityProperty>{

	private static TFileReaderBuilder instance;
	
	private TFileReaderBuilder(){
		
	}
	
	public static TFileReaderBuilder getInstance(){
		if(instance==null)
			instance = new TFileReaderBuilder();
		return instance;
	}
		
	@Override
	public Node build(Annotation annotation, TSimpleFileEntityProperty property) throws Exception {
			
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
				
				e.printStackTrace();
				
			}
		}else{
			final TSimpleFileEntityProperty<?> fileProperty = (TSimpleFileEntityProperty) property;
			try {
				final TByteArrayReader reader = new TByteArrayReader();
				reader.setShowImage(true);
				if(fileProperty.bytesProperty().getValue()==null){
					fileProperty.bytesProperty().addListener(new ChangeListener<byte[]>() {
						@Override
						public void changed(ObservableValue<? extends byte[]> arg0, byte[] arg1, byte[] arg2) {
							if(arg2!=null){
								reader.fileNameProperty().bindBidirectional(fileProperty.fileNameProperty());
								reader.valueProperty().bindBidirectional(fileProperty.bytesProperty());
							}
						}
					});
					TBytesLoader.loadBytesFromTFileEntity(fileProperty.getValue().getByteEntity().getId(), fileProperty.bytesProperty());
				}else{
					reader.fileNameProperty().bindBidirectional(fileProperty.fileNameProperty());
					reader.valueProperty().bindBidirectional(fileProperty.bytesProperty());
				}
				return reader;
			} catch (TProcessException e) {
				e.printStackTrace();
			}
			
		}
		
		return null;
	}

	
	
}
