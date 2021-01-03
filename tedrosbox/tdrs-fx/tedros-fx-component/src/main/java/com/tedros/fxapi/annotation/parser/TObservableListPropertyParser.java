package com.tedros.fxapi.annotation.parser;

import java.util.Arrays;

import javafx.collections.ObservableList;

import com.tedros.fxapi.annotation.property.TObservableListProperty;

@SuppressWarnings("rawtypes")
public class TObservableListPropertyParser extends TAnnotationParser<TObservableListProperty, ObservableList>{
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void parse(TObservableListProperty annotation, ObservableList object, String... byPass) throws Exception {
		object.addAll(Arrays.asList(annotation.addAll()));
	}
		
}
