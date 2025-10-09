package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.layout.TTitledPane;
import org.tedros.fx.annotation.parser.engine.TAnnotationParser;

import javafx.scene.control.TitledPane;

public class TTitledPaneParser extends TAnnotationParser<TTitledPane, TitledPane> {

	@Override
	public void parse(TTitledPane annotation, TitledPane object, String... byPass) throws Exception {
		super.parse(annotation, object, "fields", "layoutType");
	}
}
