package com.tedros.fxapi.annotation.parser;

import com.tedros.fxapi.annotation.control.TListViewField;

import javafx.scene.control.ListView;

@SuppressWarnings("rawtypes")
public class TListViewParser extends TAnnotationParser<TListViewField, ListView> {

	@Override
	public void parse(TListViewField annotation, ListView object, String... byPass)
			throws Exception {
		
		super.parse(annotation, object, "optionsList");
		
	}
	
}
