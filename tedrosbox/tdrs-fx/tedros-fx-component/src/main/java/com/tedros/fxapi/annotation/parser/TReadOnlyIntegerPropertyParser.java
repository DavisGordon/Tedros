package com.tedros.fxapi.annotation.parser;

import javafx.beans.property.ReadOnlyIntegerProperty;

import com.tedros.fxapi.annotation.listener.TChangeListener;
import com.tedros.fxapi.annotation.listener.TInvalidationListener;
import com.tedros.fxapi.annotation.property.TReadOnlyIntegerProperty;

public class TReadOnlyIntegerPropertyParser extends TAnnotationParser<TReadOnlyIntegerProperty, ReadOnlyIntegerProperty>{
	
	private static TReadOnlyIntegerPropertyParser instance;
	
	private TReadOnlyIntegerPropertyParser(){
		
	}
	
	public static TReadOnlyIntegerPropertyParser getInstance(){
		if(instance==null)
			instance = new TReadOnlyIntegerPropertyParser();
		return instance;
		
	}
	
	@Override
	public void parse(TReadOnlyIntegerProperty annotation, ReadOnlyIntegerProperty object, String... byPass) throws Exception {
		if(annotation.observable().addListener()!=TInvalidationListener.class)
			callParser(annotation.observable(), object, getComponentDescriptor());
		if(annotation.observableValue().addListener()!=TChangeListener.class)
			callParser(annotation.observableValue(), object, getComponentDescriptor());
		
	}
		
}
