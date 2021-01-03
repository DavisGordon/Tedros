package com.tedros.fxapi.annotation.parser;

import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;

public class THorizontalRadioGroupParser extends TAnnotationParser<THorizontalRadioGroup, com.tedros.fxapi.control.THorizontalRadioGroup> {

	@Override
		public void parse(THorizontalRadioGroup annotation, com.tedros.fxapi.control.THorizontalRadioGroup object, String... byPass) throws Exception {
			super.parse(annotation, object, "+required", "+radioButtons", "+fieldStyle" );
		}
	
}
