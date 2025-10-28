package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.control.TTableSubColumn;
import org.tedros.fx.annotation.parser.engine.TAnnotationParser;

import javafx.scene.control.TableColumn;

@SuppressWarnings("rawtypes")
public class TTableSubColumnParser extends TAnnotationParser<TTableSubColumn, TableColumn> {

	@Override
	public void parse(TTableSubColumn annotation, TableColumn object, String... byPass) throws Exception {
		super.parse(annotation, object, "cellValue", "columns");
	}
}
