/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;

import org.tedros.fx.annotation.layout.TFlowPane;
import org.tedros.fx.reader.THtmlReader;

import javafx.scene.layout.FlowPane;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TFlowPaneBuilder 
extends TBuilder 
implements ITLayoutBuilder<FlowPane> {
	
	@Override
	public FlowPane build(Annotation tAnnotation) throws Exception {
		TFlowPane ann = (TFlowPane) tAnnotation;
		FlowPane node = new FlowPane();
		callParser(ann, node);
		return node;
	}

	@Override
	public THtmlReader build(Annotation tAnnotation, THtmlReader tHtmlReader) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
}
