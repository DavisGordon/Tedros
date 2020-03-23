package com.tedros.fxapi.annotation.parser;

import javafx.beans.property.ReadOnlyBooleanProperty;

import com.tedros.fxapi.annotation.listener.TChangeListener;
import com.tedros.fxapi.annotation.listener.TInvalidationListener;
import com.tedros.fxapi.annotation.property.TReadOnlyBooleanProperty;

public class TReadOnlyBooleanPropertyParser extends TAnnotationParser<TReadOnlyBooleanProperty, ReadOnlyBooleanProperty>{
	
	private static TReadOnlyBooleanPropertyParser instance;
	
	private TReadOnlyBooleanPropertyParser(){
		
	}
	
	public static TReadOnlyBooleanPropertyParser getInstance(){
		if(instance==null)
			instance = new TReadOnlyBooleanPropertyParser();
		return instance;
		
	}
	
	@Override
	public void parse(TReadOnlyBooleanProperty annotation, ReadOnlyBooleanProperty object, String... byPass) throws Exception {
		if(annotation.observable().addListener()!=TInvalidationListener.class)
			callParser(annotation.observable(), object, getComponentDescriptor());
		if(annotation.observableValue().addListener()!=TChangeListener.class)
			callParser(annotation.observableValue(), object, getComponentDescriptor());
		
	}
		
}
