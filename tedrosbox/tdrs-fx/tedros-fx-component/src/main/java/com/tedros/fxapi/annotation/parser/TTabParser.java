package com.tedros.fxapi.annotation.parser;

import javafx.scene.control.Tab;

import com.tedros.fxapi.annotation.control.TTab;


public class TTabParser extends TAnnotationParser<TTab, Tab>{

	@Override
	public void parse(TTab annotation, Tab object, String... byPass) throws Exception {
		super.parse(annotation, object, "content", "scroll");
	}
}