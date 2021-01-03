package com.tedros.fxapi.annotation.parser;

import javafx.scene.control.TitledPane;

import com.tedros.fxapi.annotation.layout.TTitledPane;

public class TTitledPaneParser extends TAnnotationParser<TTitledPane, TitledPane> {

	@Override
	public void parse(TTitledPane annotation, TitledPane object, String... byPass) throws Exception {
		super.parse(annotation, object, "fields", "layoutType");
	}
}
