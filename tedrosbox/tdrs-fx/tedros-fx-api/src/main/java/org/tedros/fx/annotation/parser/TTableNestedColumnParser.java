package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.control.TTableNestedColumn;
import org.tedros.fx.annotation.parser.engine.TAnnotationParser;

import javafx.scene.control.TableColumn;

@SuppressWarnings("rawtypes")
public class TTableNestedColumnParser extends TAnnotationParser<TTableNestedColumn, TableColumn> {

	@Override
	public void parse(TTableNestedColumn annotation, TableColumn object, String... byPass) throws Exception {
		super.parse(annotation, object, "cellValue");
	}
}
