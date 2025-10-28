package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.control.TListViewField;
import org.tedros.fx.control.TRequiredListView;

@SuppressWarnings("rawtypes")
public class TRequiredListViewParser extends TControlFieldParser<TListViewField, TRequiredListView>{


	@Override
	public void parse(TListViewField annotation, TRequiredListView object, String... byPass) throws Exception {
		super.parse(annotation, object, "+required");
	}
}