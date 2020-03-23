/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import javafx.beans.property.Property;


/**
 * <pre>
 * The field builder class for {@link Long} types.
 * 	
 * @author davis.dun
 * </pre>
 * */
public final class TLongFieldBuilder extends TNumberFieldBuilder<com.tedros.fxapi.control.TLongField, Property<Long>> {
	
	private static TLongFieldBuilder instance;
	
	private TLongFieldBuilder(){
		
	}
	
	/**
	 * <pre>
	 * Return a {@link TLongFieldBuilder} instance.
	 * </pre>
	 * */
	public static TLongFieldBuilder getInstance(){
		if(instance==null)
			instance = new TLongFieldBuilder();
		return instance;
	}
	
}
