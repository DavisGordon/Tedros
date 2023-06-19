package org.tedros.fx.annotation.parser;

import java.lang.annotation.Annotation;

import javafx.scene.layout.StackPane;

public final class TStackPaneParser extends TAnnotationParser<Annotation, StackPane> {

	@Override
	public void parse(Annotation annotation, StackPane object, String... byPass) throws Exception {
		super.parse(annotation, object, "required", "items", "height", "width", "model", "modelView",
				"selectionMode", "sourceLabel","targetLabel","process","layout","fields","showButtons");
	}
}
