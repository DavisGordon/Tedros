package com.tedros.fxapi.annotation.parser;

import com.tedros.fxapi.annotation.control.TListViewField;
import com.tedros.fxapi.control.TRequiredListView;

@SuppressWarnings("rawtypes")
public class TRequiredListViewParser extends TControlFieldParser<TListViewField, TRequiredListView>{


	@Override
	public void parse(TListViewField annotation, TRequiredListView object, String... byPass) throws Exception {
		super.parse(annotation, object, "+required");
	}
}