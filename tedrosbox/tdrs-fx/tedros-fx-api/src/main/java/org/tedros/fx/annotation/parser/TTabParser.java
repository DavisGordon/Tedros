package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.control.TTab;

import javafx.scene.control.Tab;


public class TTabParser extends TAnnotationParser<TTab, Tab>{

	@Override
	public void parse(TTab annotation, Tab object, String... byPass) throws Exception {
		super.parse(annotation, object, "content", "scroll");
	}
}