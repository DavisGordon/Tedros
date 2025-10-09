package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.parser.engine.TAnnotationParser;

import javafx.scene.control.TextField;

public class TextFieldParser extends TAnnotationParser<TTextField, TextField> {

	@Override
	public void parse(TTextField annotation, TextField object, String... byPass)
			throws Exception {
		super.parse(annotation, object, "maxLength","required","query","service","modelViewType","startSearchAt");
	}
}
