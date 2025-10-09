package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.control.TListViewField;
import org.tedros.fx.annotation.parser.engine.TAnnotationParser;

import javafx.scene.control.ListView;

@SuppressWarnings("rawtypes")
public class TListViewParser extends TAnnotationParser<TListViewField, ListView> {

	@Override
	public void parse(TListViewField annotation, ListView object, String... byPass)
			throws Exception {
		
		super.parse(annotation, object, "optionsList");
		
	}
	
}
