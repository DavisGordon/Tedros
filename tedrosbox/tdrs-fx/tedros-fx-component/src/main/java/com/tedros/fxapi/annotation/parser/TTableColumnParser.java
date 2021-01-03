package com.tedros.fxapi.annotation.parser;

import javafx.scene.control.TableColumn;

import com.tedros.fxapi.annotation.control.TTableColumn;

@SuppressWarnings("rawtypes")
public class TTableColumnParser extends TAnnotationParser<TTableColumn, TableColumn> {

	@Override
	public void parse(TTableColumn annotation, TableColumn object, String... byPass) throws Exception {
		super.parse(annotation, object, "cellValue", "columns", "cellFactory");
	}
}
