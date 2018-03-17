package com.tedros.fxapi.control;


public class TIntegerField extends TNumberField<Integer> {

	@Override
	Class<?> getNumberClassType() {
		return Integer.class;	
	}
	
}
