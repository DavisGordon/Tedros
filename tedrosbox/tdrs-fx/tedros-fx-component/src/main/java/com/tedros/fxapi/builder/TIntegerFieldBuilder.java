/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import javafx.beans.property.Property;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TIntegerFieldBuilder extends TNumberFieldBuilder<com.tedros.fxapi.control.TIntegerField, Property<Integer>> {

	private static TIntegerFieldBuilder instance;
	
	private TIntegerFieldBuilder(){
		
	}
	
	public static TIntegerFieldBuilder getInstance(){
		if(instance==null)
			instance = new TIntegerFieldBuilder();
		return instance;
	}
	
}
