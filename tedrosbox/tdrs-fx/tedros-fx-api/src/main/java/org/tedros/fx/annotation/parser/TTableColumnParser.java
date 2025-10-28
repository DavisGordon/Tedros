package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.control.TTableColumn;
import org.tedros.fx.annotation.parser.engine.TAnnotationParser;

import javafx.scene.control.TableColumn;

@SuppressWarnings("rawtypes")
public class TTableColumnParser extends TAnnotationParser<TTableColumn, TableColumn> {

	@Override
	public void parse(TTableColumn annotation, TableColumn object, String... byPass) throws Exception {
		super.parse(annotation, object, "cellValue", "columns", "cellFactory");
	}
}
