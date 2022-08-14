/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 18/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Node;

import org.apache.commons.lang3.StringUtils;
import org.tedros.fx.form.TConverter;
import org.tedros.fx.presenter.model.TModelView;
import org.tedros.fx.reader.TByteArrayReader;

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

	@SuppressWarnings("unchecked")
	@Override
	public Node build(Annotation annotation, SimpleObjectProperty property) throws Exception {
			
		org.tedros.fx.annotation.reader.TByteArrayReader tAnnotation = (org.tedros.fx.annotation.reader.TByteArrayReader) annotation;
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
