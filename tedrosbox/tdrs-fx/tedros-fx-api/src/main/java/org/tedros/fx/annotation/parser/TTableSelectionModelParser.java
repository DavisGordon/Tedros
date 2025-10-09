package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.control.TTableView.TTableViewSelectionModel;
import org.tedros.fx.annotation.parser.engine.TAnnotationParser;

import javafx.scene.control.TableSelectionModel;


@SuppressWarnings("rawtypes")
public class TTableSelectionModelParser 
extends TAnnotationParser<TTableViewSelectionModel, TableSelectionModel>{

	@Override
	public void parse(TTableViewSelectionModel annotation, TableSelectionModel object, String... byPass) throws Exception {
		super.parse(annotation, object, "+cellSelectionEnabled", "+cellSelectionEnabledProperty");
	}
}