package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.control.TSliderField;
import org.tedros.fx.control.TRequiredSlider;

public class TRequiredSliderParser extends TControlFieldParser<TSliderField, TRequiredSlider>{

	@Override
	public void parse(TSliderField annotation, TRequiredSlider object,
			String... byPass) throws Exception {
		super.parse(annotation, object, "+validate");
	}
	
}