package org.tedros.fx.annotation.parser;

import java.lang.annotation.Annotation;

import org.tedros.fx.control.TRequiredDatePicker;

public class TRequiredDatePickerParser<A extends Annotation> extends TControlFieldParser<A, TRequiredDatePicker>{

	@Override
	public void parse(A annotation, TRequiredDatePicker object,
			String... byPass) throws Exception {
	
		super.parse(annotation, object, "+required");
	}
}