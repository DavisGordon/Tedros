package org.tedros.fx.annotation.parser;

import java.util.Arrays;

import org.tedros.fx.annotation.property.TObservableListProperty;

import javafx.collections.ObservableList;

@SuppressWarnings("rawtypes")
public class TObservableListPropertyParser extends TAnnotationParser<TObservableListProperty, ObservableList>{
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void parse(TObservableListProperty annotation, ObservableList object, String... byPass) throws Exception {
		object.addAll(Arrays.asList(annotation.addAll()));
	}
		
}
