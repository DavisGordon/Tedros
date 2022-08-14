package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.control.THorizontalRadioGroup;

public class THorizontalRadioGroupParser extends TAnnotationParser<THorizontalRadioGroup, org.tedros.fx.control.THorizontalRadioGroup> {

	@Override
		public void parse(THorizontalRadioGroup annotation, org.tedros.fx.control.THorizontalRadioGroup object, String... byPass) throws Exception {
			super.parse(annotation, object, "+required", "+radioButtons", "+fieldStyle" );
		}
	
}
