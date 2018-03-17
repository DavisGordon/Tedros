/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;

import com.tedros.fxapi.annotation.layout.TVBox;
import com.tedros.fxapi.descriptor.TComponentDescriptor;
import com.tedros.fxapi.domain.TLayoutType;
import com.tedros.fxapi.form.TComponentBuilder;
import com.tedros.fxapi.form.TFieldBox;
import com.tedros.fxapi.html.THtmlLayoutGenerator;
import com.tedros.fxapi.reader.THtmlReader;

import javafx.scene.Node;
import javafx.scene.layout.VBox;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TVBoxBuilder 
extends TBuilder 
implements ITLayoutBuilder<VBox> {
	
	private static TVBoxBuilder instance;
	
	private TVBoxBuilder(){
		
	}
	
	public static TVBoxBuilder getInstance(){
		if(instance==null)
			instance = new TVBoxBuilder();
		return instance;
	}

	@Override
	public VBox build(Annotation tAnnotation, TFieldBox fieldBox) throws Exception {
		TVBox tvBox = (TVBox) tAnnotation;
		VBox node = new VBox();
		node.getChildren().add(fieldBox);
		callParser(tvBox, node);
		return node;
	}

	@Override
	public THtmlReader build(Annotation annotation, THtmlReader tHtmlReader)
			throws Exception {
		
		TVBox tAnnotation = (TVBox) annotation;
		
		THtmlLayoutGenerator tHtmlLayoutGenerator = new THtmlLayoutGenerator(TLayoutType.VBOX);
		
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
