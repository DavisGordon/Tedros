package com.tedros.fxapi.annotation.parser;

import com.tedros.fxapi.annotation.control.TSliderField;
import com.tedros.fxapi.control.TRequiredSlider;

public class TRequiredSliderParser extends TControlFieldParser<TSliderField, TRequiredSlider>{

	@Override
	public void parse(TSliderField annotation, TRequiredSlider object,
			String... byPass) throws Exception {
	
		super.parse(annotation, object, "+zeroValidation");
	}
	
}