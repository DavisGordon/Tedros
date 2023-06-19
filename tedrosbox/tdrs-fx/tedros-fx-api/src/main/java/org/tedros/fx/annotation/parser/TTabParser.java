package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.control.TTab;

import javafx.scene.control.Tab;


public class TTabParser extends TAnnotationParser<TTab, Tab>{

	@Override
	public void parse(TTab ann, Tab tab, String... byPass) throws Exception {
		tab.setClosable(ann.closable());
		super.parse(ann, tab, "fields", "scroll", "closable", "orientation");
	}
}