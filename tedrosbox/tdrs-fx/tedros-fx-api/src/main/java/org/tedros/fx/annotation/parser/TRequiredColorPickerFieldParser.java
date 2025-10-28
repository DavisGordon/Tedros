package org.tedros.fx.annotation.parser;

import java.lang.annotation.Annotation;

import org.tedros.fx.control.TRequiredColorPicker;

public class TRequiredColorPickerFieldParser<A extends Annotation> extends TControlFieldParser<A, TRequiredColorPicker>{

	@Override
	public void parse(A annotation, TRequiredColorPicker object,
			String... byPass) throws Exception {
	
		super.parse(annotation, object, "+required");
	}
}