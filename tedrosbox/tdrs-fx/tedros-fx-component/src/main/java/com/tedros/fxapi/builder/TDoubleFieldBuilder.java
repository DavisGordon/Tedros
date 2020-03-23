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
public final class TDoubleFieldBuilder extends  TNumberFieldBuilder<com.tedros.fxapi.control.TDoubleField, Property<Double>> {

	private static TDoubleFieldBuilder instance;
	
	private TDoubleFieldBuilder() {
		
	}
	
	public static TDoubleFieldBuilder getInstance(){
		if(instance==null)
			instance = new TDoubleFieldBuilder();
		return instance;
	}
}
