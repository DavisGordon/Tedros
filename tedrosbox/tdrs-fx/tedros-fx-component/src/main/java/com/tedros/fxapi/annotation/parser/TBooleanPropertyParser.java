package com.tedros.fxapi.annotation.parser;

import javafx.beans.property.BooleanProperty;

import com.tedros.fxapi.annotation.listener.TChangeListener;
import com.tedros.fxapi.annotation.listener.TInvalidationListener;
import com.tedros.fxapi.annotation.property.TBooleanProperty;

public class TBooleanPropertyParser extends TAnnotationParser<TBooleanProperty, BooleanProperty>{

	@Override
	public void parse(TBooleanProperty annotation, BooleanProperty object, String... byPass) throws Exception {
		if(annotation.observable().addListener()!=TInvalidationListener.class)
			callParser(annotation.observable(), object, getComponentDescriptor());
		if(annotation.observableValue().addListener()!=TChangeListener.class)
			callParser(annotation.observableValue(), object, getComponentDescriptor());
		
	}
		
}
