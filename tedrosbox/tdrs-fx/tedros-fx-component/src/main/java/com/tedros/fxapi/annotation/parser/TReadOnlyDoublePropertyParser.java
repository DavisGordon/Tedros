package com.tedros.fxapi.annotation.parser;

import javafx.beans.property.ReadOnlyDoubleProperty;

import com.tedros.fxapi.annotation.listener.TChangeListener;
import com.tedros.fxapi.annotation.listener.TInvalidationListener;
import com.tedros.fxapi.annotation.property.TReadOnlyDoubleProperty;

public class TReadOnlyDoublePropertyParser extends TAnnotationParser<TReadOnlyDoubleProperty, ReadOnlyDoubleProperty>{
	
	private static TReadOnlyDoublePropertyParser instance;
	
	private TReadOnlyDoublePropertyParser(){
		
	}
	
	public static TReadOnlyDoublePropertyParser getInstance(){
		if(instance==null)
			instance = new TReadOnlyDoublePropertyParser();
		return instance;
		
	}
	
	@Override
	public void parse(TReadOnlyDoubleProperty annotation, ReadOnlyDoubleProperty object, String... byPass) throws Exception {
		if(annotation.observable().addListener()!=TInvalidationListener.class)
			callParser(annotation.observable(), object, getComponentDescriptor());
		if(annotation.observableValue().addListener()!=TChangeListener.class)
			callParser(annotation.observableValue(), object, getComponentDescriptor());
		
	}
		
}
