package com.tedros.fxapi.control;

import java.math.BigDecimal;


public class TBigDecimalField extends TNumberField<BigDecimal> {

	@Override
	Class<?> getNumberClassType() {
		return BigDecimal.class;
	}
	
}
