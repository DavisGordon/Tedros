package org.tedros.fx.annotation.parser;

import java.lang.annotation.Annotation;

import org.tedros.fx.annotation.parser.engine.TAnnotationParser;

public final class TStackPaneParser extends TAnnotationParser<Annotation, Object> {

	@Override
	public void parse(Annotation annotation, Object object, String... byPass) throws Exception {
		super.parse(annotation, object, "required", "items", "height", "width", "model", "modelView",
				"selectionMode", "sourceLabel","targetLabel","process","layout","fields","showButtons", "textAlignment");
	}
}
