package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.control.TRequiredTextArea;

public class TRequiredTextAreaParser extends TAnnotationParser<TTextAreaField, org.tedros.fx.control.TRequiredTextArea> {
	
	@Override
	public void parse(TTextAreaField annotation, TRequiredTextArea object,
			String... byPass) throws Exception {
	
		super.parse(annotation, object, "+required");
	}
}
