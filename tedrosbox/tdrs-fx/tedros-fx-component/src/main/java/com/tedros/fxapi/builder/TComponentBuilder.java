/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

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
		final com.tedros.fxapi.control.TNumberSpinnerField control = new com.tedros.fxapi.control.TNumberSpinnerField();
		/*callParser(tAnnotation, control);
		control.valueProperty().bindBidirectional(attrProperty);*/
		return control;
	}
	
	
}
