package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.control.TPasswordField;
import org.tedros.fx.annotation.parser.engine.TAnnotationParser;

import javafx.scene.control.PasswordField;

public class TPasswordFieldParser extends TAnnotationParser<TPasswordField, PasswordField> {

	@Override
	public void parse(TPasswordField annotation, PasswordField object, String... byPass) throws Exception {
		super.parse(annotation, object, "required");
	}
}
