package com.tedros.fxapi.annotation.parser;

import javafx.beans.property.ReadOnlyStringProperty;

import com.tedros.fxapi.annotation.listener.TChangeListener;
import com.tedros.fxapi.annotation.listener.TInvalidationListener;
import com.tedros.fxapi.annotation.property.TReadOnlyStringProperty;

public class TReadOnlyStringPropertyParser extends TAnnotationParser<TReadOnlyStringProperty, ReadOnlyStringProperty>{
	
	private static TReadOnlyStringPropertyParser instance;
	
	private TReadOnlyStringPropertyParser(){
		
	}
	
	public static TReadOnlyStringPropertyParser getInstance(){
		if(instance==null)
			instance = new TReadOnlyStringPropertyParser();
		return instance;
		
	}
	
	@Override
	public void parse(TReadOnlyStringProperty annotation, ReadOnlyStringProperty object, String... byPass) throws Exception {
		if(annotation.observable().addListener()!=TInvalidationListener.class)
			callParser(annotation.observable(), object, getComponentDescriptor());
		if(annotation.observableValue().addListener()!=TChangeListener.class)
			callParser(annotation.observableValue(), object, getComponentDescriptor());
		
	}
		
}
