package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.control.THRadioGroup;

public class THRadioGroupParser extends TAnnotationParser<THRadioGroup, org.tedros.fx.control.THRadioGroup> {

	@Override
		public void parse(THRadioGroup annotation, org.tedros.fx.control.THRadioGroup object, String... byPass) throws Exception {
			super.parse(annotation, object, "radio" );
		}
}
