/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import java.math.BigDecimal;

import javafx.beans.property.Property;


/**
 * <pre>
 * The field builder class for {@link BigDecimal} types.
 * 	
 * @author davis.dun
 * </pre>
 * */
public class TBigDecimalFieldBuilder extends TNumberFieldBuilder<com.tedros.fxapi.control.TBigDecimalField, Property<BigDecimal>> {


	private static TBigDecimalFieldBuilder instance;
	
	private TBigDecimalFieldBuilder(){
		
	}
	
	/**
	 * <pre>
	 * Return a {@link TBigDecimalFieldBuilder} instance.
	 * </pre>
	 * */
	public static TBigDecimalFieldBuilder getInstance(){
		if(instance==null)
			instance = new TBigDecimalFieldBuilder();
		return instance;
	}
}
