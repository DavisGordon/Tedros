package com.tedros.fxapi.annotation.parser;

import javafx.beans.property.IntegerProperty;

import com.tedros.fxapi.annotation.listener.TChangeListener;
import com.tedros.fxapi.annotation.listener.TInvalidationListener;
import com.tedros.fxapi.annotation.property.TIntegerProperty;


public class TIntegerPropertyParser extends TAnnotationParser<TIntegerProperty, IntegerProperty>{
	
	private static TIntegerPropertyParser instance;
	
	private TIntegerPropertyParser(){
		
	}
	
	public static TIntegerPropertyParser getInstance(){
		if(instance==null)
			instance = new TIntegerPropertyParser();
		return instance;
		
	}
	
	@Override
	public void parse(TIntegerProperty annotation, IntegerProperty object, String... byPass) throws Exception {
		if(annotation.observable().addListener()!=TInvalidationListener.class)
			callParser(annotation.observable(), object, getComponentDescriptor());
		if(annotation.observableValue().addListener()!=TChangeListener.class)
			callParser(annotation.observableValue(), object, getComponentDescriptor());
		
	}
		
}
