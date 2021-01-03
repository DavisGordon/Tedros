package com.tedros.fxapi.annotation.parser;

import javafx.beans.property.DoubleProperty;

import com.tedros.fxapi.annotation.listener.TChangeListener;
import com.tedros.fxapi.annotation.listener.TInvalidationListener;
import com.tedros.fxapi.annotation.property.TDoubleProperty;

public class TDoublePropertyParser extends TAnnotationParser<TDoubleProperty, DoubleProperty>{
	
	
	
	@Override
	public void parse(TDoubleProperty annotation, DoubleProperty object, String... byPass) throws Exception {
		if(annotation.observable().addListener()!=TInvalidationListener.class)
			callParser(annotation.observable(), object, getComponentDescriptor());
		if(annotation.observableValue().addListener()!=TChangeListener.class)
			callParser(annotation.observableValue(), object, getComponentDescriptor());
		
	}
		
}
