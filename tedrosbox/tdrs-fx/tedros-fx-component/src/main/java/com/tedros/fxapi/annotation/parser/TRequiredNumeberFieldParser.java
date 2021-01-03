package com.tedros.fxapi.annotation.parser;

import java.lang.annotation.Annotation;

import com.tedros.fxapi.control.TRequiredNumberField;

public class TRequiredNumeberFieldParser<A extends Annotation> extends TControlFieldParser<A, TRequiredNumberField>{

	
	
	@Override
	public void parse(A annotation, TRequiredNumberField object,
			String... byPass) throws Exception {
		// TODO Auto-generated method stub
		super.parse(annotation, object, "+zeroValidation");
	}
}