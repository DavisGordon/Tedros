package com.tedros.fxapi.annotation.parser;

import java.lang.annotation.Annotation;

import com.tedros.fxapi.control.TRequiredDatePicker;

public class TRequiredDatePickerParser<A extends Annotation> extends TControlFieldParser<A, TRequiredDatePicker>{

	@Override
	public void parse(A annotation, TRequiredDatePicker object,
			String... byPass) throws Exception {
	
		super.parse(annotation, object, "+required");
	}
}