/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 18/01/2014
 */
package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Node;

import org.apache.commons.lang3.StringUtils;

import com.tedros.fxapi.form.TConverter;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.fxapi.reader.TByteArrayReader;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public final class TByteArrayReaderBuilder 
extends TBuilder 
implements ITReaderBuilder<Node, SimpleObjectProperty>{

	private static TByteArrayReaderBuilder instance;
	
	private TByteArrayReaderBuilder(){
		
	}
	
	public static TByteArrayReaderBuilder getInstance(){
		if(instance==null)
			instance = new TByteArrayReaderBuilder();
		return instance;
	}
		
	@SuppressWarnings("unchecked")
	@Override
	public Node build(Annotation annotation, SimpleObjectProperty property) throws Exception {
			
		com.tedros.fxapi.annotation.reader.TByteArrayReader tAnnotation = (com.tedros.fxapi.annotation.reader.TByteArrayReader) annotation;
		TModelView modelView = (TModelView) getComponentDescriptor().getModelView();
			
		if(tAnnotation.converter().type()!=TConverter.class){
			Class clazz = tAnnotation.converter().type();
			TConverter converter = (TConverter) clazz.getConstructor(Object.class).newInstance(property);
			Node node = (Node) converter.getOut();
			callParser(tAnnotation, node);
			if(node!=null && StringUtils.isBlank(node.getId()))
				node.setId("t-reader-label");
			return node;
		}
		
		if (property instanceof SimpleObjectProperty){
			final Method fileNamePropertyGetMethod = modelView.getClass().getMethod("get"+StringUtils.capitalize(tAnnotation.fileNameField()));
			final SimpleStringProperty fileNameProperty = (SimpleStringProperty) fileNamePropertyGetMethod.invoke(modelView);
			final SimpleObjectProperty<byte[]> byteProperty = (SimpleObjectProperty<byte[]>) property;
			final TByteArrayReader reader = new TByteArrayReader();
			reader.fileNameProperty().bindBidirectional(fileNameProperty);
			reader.valueProperty().bindBidirectional(byteProperty);
			callParser(annotation, reader);
			if(reader!=null && StringUtils.isBlank(reader.getId()))
				reader.setId("t-reader-label");
			return reader;
		}
		
		return null;
	}

	
	
}
