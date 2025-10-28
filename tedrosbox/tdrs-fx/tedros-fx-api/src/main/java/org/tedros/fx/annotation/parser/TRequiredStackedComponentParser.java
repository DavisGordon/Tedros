package org.tedros.fx.annotation.parser;

import java.lang.annotation.Annotation;

import org.tedros.fx.annotation.parser.engine.TAnnotationParser;
import org.tedros.fx.control.TRequiredStackedComponent;

public class TRequiredStackedComponentParser extends TAnnotationParser<Annotation, TRequiredStackedComponent> {
	@Override
	public void parse(Annotation annotation, TRequiredStackedComponent object, String... byPass) throws Exception {
		super.parse(annotation, object, "+required");
	}
}
