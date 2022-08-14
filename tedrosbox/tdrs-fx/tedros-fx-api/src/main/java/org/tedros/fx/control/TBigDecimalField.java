package org.tedros.fx.control;

import java.math.BigDecimal;


public class TBigDecimalField extends TNumberField<BigDecimal> {

	@Override
	Class<?> getNumberClassType() {
		return BigDecimal.class;
	}
	
}
