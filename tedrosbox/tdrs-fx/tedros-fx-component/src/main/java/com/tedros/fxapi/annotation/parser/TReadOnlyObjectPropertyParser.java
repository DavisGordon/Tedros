package com.tedros.fxapi.annotation.parser;

import javafx.beans.property.ReadOnlyObjectProperty;

import com.tedros.fxapi.annotation.listener.TChangeListener;
import com.tedros.fxapi.annotation.listener.TInvalidationListener;
import com.tedros.fxapi.annotation.property.TReadOnlyObjectProperty;

@SuppressWarnings("rawtypes")
public class TReadOnlyObjectPropertyParser extends TAnnotationParser<TReadOnlyObjectProperty, ReadOnlyObjectProperty>{
	
	private static TReadOnlyObjectPropertyParser instance;
	
	private TReadOnlyObjectPropertyParser(){
		
	}
	
	public static TReadOnlyObjectPropertyParser getInstance(){
		if(instance==null)
			instance = new TReadOnlyObjectPropertyParser();
		return instance;
		
	}
	
	@Override
	public void parse(TReadOnlyObjectProperty annotation, ReadOnlyObjectProperty object, String... byPass) throws Exception {
		if(annotation.observable().addListener()!=TInvalidationListener.class)
			callParser(annotation.observable(), object, getComponentDescriptor());
		if(annotation.observableValue().addListener()!=TChangeListener.class)
			callParser(annotation.observableValue(), object, getComponentDescriptor());
		
	}
		
}
