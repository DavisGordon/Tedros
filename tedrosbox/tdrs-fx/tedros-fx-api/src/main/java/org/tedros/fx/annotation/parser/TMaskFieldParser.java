package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.control.TMaskField;
import org.tedros.fx.annotation.parser.engine.TAnnotationParser;

public class TMaskFieldParser extends TAnnotationParser<TMaskField, org.tedros.fx.control.TMaskField> {
	
	@Override
	public void parse(TMaskField annotation, org.tedros.fx.control.TMaskField object, String... byPass) throws Exception {
		super.parse(annotation, object, "node", "control", "pane");
	}
}
