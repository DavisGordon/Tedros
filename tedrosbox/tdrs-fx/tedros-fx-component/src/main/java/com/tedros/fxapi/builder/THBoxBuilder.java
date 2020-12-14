/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.descriptor.TComponentDescriptor;
import com.tedros.fxapi.domain.TLayoutType;
import com.tedros.fxapi.form.TComponentBuilder;
import com.tedros.fxapi.form.TFieldBox;
import com.tedros.fxapi.html.THtmlLayoutGenerator;
import com.tedros.fxapi.reader.THtmlReader;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class THBoxBuilder 
extends TBuilder 
implements ITLayoutBuilder<HBox> {
	
	private static THBoxBuilder instance;
	
	private THBoxBuilder(){
		
	}
	
	public static THBoxBuilder getInstance(){
		if(instance==null)
			instance = new THBoxBuilder();
		return instance;
	}

	@Override
	public HBox build(Annotation tAnnotation, TFieldBox fieldBox) throws Exception {
		THBox thBox = (THBox) tAnnotation;
		HBox node = new HBox();
		node.setUserData(fieldBox);
		//node.getChildren().add(fieldBox);
		callParser(thBox, node);
		return node;
	}

	@Override
	public THtmlReader build(Annotation annotation, THtmlReader tHtmlReader)
			throws Exception {
		
		THBox tAnnotation = (THBox) annotation;
		THtmlLayoutGenerator tHtmlLayoutGenerator = new THtmlLayoutGenerator(TLayoutType.HBOX);
		
		for(String field : tAnnotation.pane().children()){
			Node node = null;
			if(getComponentDescriptor().getFieldDescriptor().getFieldName().equals(field)){
				node = tHtmlReader;
			}else{
				final TComponentDescriptor descriptor = new TComponentDescriptor(getComponentDescriptor(), field);
				node = TComponentBuilder.getField(descriptor);
			}
			
			if(node==null)
				continue;
			
			if(!(node instanceof THtmlReader)){
				System.err.println("TWarning from "+this.getClass().getSimpleName()
						+": The field "+field+" in "+getComponentDescriptor().getModelView().getClass().getName()
						+"  must be annotated with @TReaderHtml()");
			}else{
				THtmlReader fieldHtmlReader = (THtmlReader) node;
				tHtmlLayoutGenerator.addContent(fieldHtmlReader.getText());
			}
		}
		
		
		
		return new THtmlReader(tHtmlLayoutGenerator.getHtml());
	}
	
	
	
}
