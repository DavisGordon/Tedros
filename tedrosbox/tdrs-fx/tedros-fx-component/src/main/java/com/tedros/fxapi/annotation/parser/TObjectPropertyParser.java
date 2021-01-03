package com.tedros.fxapi.annotation.parser;

import javafx.beans.property.ObjectProperty;

import com.tedros.fxapi.annotation.listener.TChangeListener;
import com.tedros.fxapi.annotation.listener.TInvalidationListener;
import com.tedros.fxapi.annotation.property.TObjectProperty;

@SuppressWarnings("rawtypes")
public class TObjectPropertyParser extends TAnnotationParser<TObjectProperty, ObjectProperty>{
	
	@Override
	public void parse(TObjectProperty annotation, ObjectProperty object, String... byPass) throws Exception {
		if(annotation.observable().addListener()!=TInvalidationListener.class)
			callParser(annotation.observable(), object, getComponentDescriptor());
		if(annotation.observableValue().addListener()!=TChangeListener.class)
			callParser(annotation.observableValue(), object, getComponentDescriptor());
		
	}
		
}
