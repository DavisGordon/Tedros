package org.tedros.fx.annotation.parser;

import java.lang.annotation.Annotation;

import javafx.scene.layout.VBox;

public class TVBoxParser extends TAnnotationParser<Annotation, VBox> {

	@Override
	public void parse(Annotation annotation, VBox object, String... byPass) throws Exception {
		super.parse(annotation, object, "height", "width","modalHeight","modalWidth",
				"mode", "required", "modelClass", "modelViewClass", "radioButtons");
	}
}
