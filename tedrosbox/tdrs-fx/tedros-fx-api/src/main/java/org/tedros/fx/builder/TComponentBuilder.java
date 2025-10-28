/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;

import javafx.scene.Node;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public final class TComponentBuilder
extends TBuilder
implements ITComponentBuilder<Object> {

	private static TComponentBuilder instance;
	
	private TComponentBuilder(){
		
	}
	
	public static  TComponentBuilder getInstance(){
		if(instance==null)
			instance = new TComponentBuilder();
		return instance;	
	}

	public Node build(Annotation tComponent, Object fieldObject ) {
		//final TNumberSpinnerField tAnnotation = (TNumberSpinnerField) annotation;
		final org.tedros.fx.control.TNumberSpinnerField control = new org.tedros.fx.control.TNumberSpinnerField();
		/*callParser(tAnnotation, control);
		control.valueProperty().bindBidirectional(attrProperty);*/
		return control;
	}
	
	
}
