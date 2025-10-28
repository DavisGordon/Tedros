/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;

import org.tedros.fx.annotation.layout.TGridPane;
import org.tedros.fx.domain.TLayoutType;
import org.tedros.fx.html.THtmlLayoutGenerator;
import org.tedros.fx.reader.THtmlReader;

import javafx.scene.layout.GridPane;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TGridPaneBuilder 
extends TBuilder 
implements ITLayoutBuilder<GridPane> {
	
	@Override
	public GridPane build(Annotation tAnnotation) throws Exception {
		TGridPane ann = (TGridPane) tAnnotation;
		GridPane node = new GridPane();
		callParser(ann, node);
		return node;
	}

	@Override
	public THtmlReader build(Annotation annotation, THtmlReader tHtmlReader)
			throws Exception {
		
		//TBorderPane ann = (TBorderPane) annotation;
		
		THtmlLayoutGenerator tHtmlLayoutGenerator = new THtmlLayoutGenerator(TLayoutType.VBOX);
		/*
		for(String field : annotation.pane().children()){
			Node node = null;
			if(tHtmlReader!=null && getComponentDescriptor().getFieldDescriptor().getFieldName().equals(field)){
				node = tHtmlReader;
			}else{
				final TComponentDescriptor descriptor = new TComponentDescriptor(getComponentDescriptor(), field);
				node = TControlLayoutReaderBuilder.getField(descriptor);
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
		*/
		
		
		return new THtmlReader(tHtmlLayoutGenerator.getHtml());
		
	}
	
	
	
}
