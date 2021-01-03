package com.tedros.fxapi.annotation.parser;

import javafx.scene.control.TableColumn;

import com.tedros.fxapi.annotation.control.TTableNestedColumn;

@SuppressWarnings("rawtypes")
public class TTableNestedColumnParser extends TAnnotationParser<TTableNestedColumn, TableColumn> {

	@Override
	public void parse(TTableNestedColumn annotation, TableColumn object, String... byPass) throws Exception {
		super.parse(annotation, object, "cellValue");
	}
}
