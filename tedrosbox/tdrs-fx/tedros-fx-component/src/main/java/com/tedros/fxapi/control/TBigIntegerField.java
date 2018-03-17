package com.tedros.fxapi.control;

import java.math.BigInteger;


public class TBigIntegerField extends TNumberField<BigInteger> {

	@Override
	Class<?> getNumberClassType() {
		return BigInteger.class;
	}
	
}
