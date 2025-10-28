/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;

import org.tedros.fx.annotation.layout.TVBox;
import org.tedros.fx.descriptor.TComponentDescriptor;
import org.tedros.fx.domain.TLayoutType;
import org.tedros.fx.form.TComponentReaderBuilder;
import org.tedros.fx.html.THtmlLayoutGenerator;
import org.tedros.fx.reader.THtmlReader;
import org.tedros.util.TLoggerUtil;

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
	

	@Override
	public VBox build(Annotation tAnnotation) throws Exception {
		TVBox tvBox = (TVBox) tAnnotation;
		VBox node = new VBox();
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
			if(tHtmlReader!=null && getComponentDescriptor().getFieldDescriptor().getFieldName().equals(field)){
				node = tHtmlReader;
			}else{
				final TComponentDescriptor descriptor = new TComponentDescriptor(getComponentDescriptor(), field);
				node = TComponentReaderBuilder.getField(descriptor);
			}
			
			if(node==null)
				continue;
			
			if(!(node instanceof THtmlReader)){
				TLoggerUtil.warn(getClass(), "The field "+field+" in "+getComponentDescriptor().getModelView().getClass().getName()
						+"  must be annotated with @TReaderHtml()");
			}else{
				THtmlReader fieldHtmlReader = (THtmlReader) node;
				tHtmlLayoutGenerator.addContent(fieldHtmlReader.getText());
			}
		}
		
		
		
		return new THtmlReader(tHtmlLayoutGenerator.getHtml());
		
	}
	
	
	
}
