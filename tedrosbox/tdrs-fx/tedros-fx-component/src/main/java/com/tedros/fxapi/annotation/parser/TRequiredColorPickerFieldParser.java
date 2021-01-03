package com.tedros.fxapi.annotation.parser;

import java.lang.annotation.Annotation;

import com.tedros.fxapi.control.TRequiredColorPicker;

public class TRequiredColorPickerFieldParser<A extends Annotation> extends TControlFieldParser<A, TRequiredColorPicker>{

	@Override
	public void parse(A annotation, TRequiredColorPicker object,
			String... byPass) throws Exception {
	
		super.parse(annotation, object, "+required");
	}
}