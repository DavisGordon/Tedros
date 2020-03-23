package com.tedros.fxapi.control;


public class TLongField extends TNumberField<Long> {

	@Override
	Class<?> getNumberClassType() {
		return Long.class;	
	}
		
}
