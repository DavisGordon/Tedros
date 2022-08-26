package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.control.TTableView.TTableViewSelectionModel;

import javafx.scene.control.MultipleSelectionModel;


@SuppressWarnings("rawtypes")
public class TMultipleSelectionModelParser 
extends TAnnotationParser<TTableViewSelectionModel, MultipleSelectionModel>{

	@Override
	public void parse(TTableViewSelectionModel annotation, MultipleSelectionModel object, String... byPass) throws Exception {
		super.parse(annotation, object, "+selectionMode");
	}
}