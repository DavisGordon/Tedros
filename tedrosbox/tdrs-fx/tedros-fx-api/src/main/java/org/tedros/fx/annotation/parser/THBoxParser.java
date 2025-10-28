package org.tedros.fx.annotation.parser;

import java.lang.annotation.Annotation;

import org.tedros.fx.annotation.parser.engine.TAnnotationParser;

import javafx.scene.layout.HBox;

public class THBoxParser extends TAnnotationParser<Annotation, HBox> {
	@Override
	public void parse(Annotation annotation, HBox object, String... byPass) throws Exception {
		super.parse(annotation, object, "mode", "radioButtons");
	}
}
