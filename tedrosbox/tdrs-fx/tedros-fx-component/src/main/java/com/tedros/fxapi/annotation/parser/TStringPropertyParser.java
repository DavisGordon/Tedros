package com.tedros.fxapi.annotation.parser;

import javafx.beans.property.StringProperty;

import com.tedros.fxapi.annotation.listener.TChangeListener;
import com.tedros.fxapi.annotation.listener.TInvalidationListener;
import com.tedros.fxapi.annotation.property.TStringProperty;

public class TStringPropertyParser extends TAnnotationParser<TStringProperty, StringProperty>{
	
	private static TStringPropertyParser instance;
	
	private TStringPropertyParser(){
		
	}
	
	public static TStringPropertyParser getInstance(){
		if(instance==null)
			instance = new TStringPropertyParser();
		return instance;
		
	}
	
	@Override
	public void parse(TStringProperty annotation, StringProperty object, String... byPass) throws Exception {
		if(annotation.observable().addListener()!=TInvalidationListener.class)
			callParser(annotation.observable(), object, getComponentDescriptor());
		if(annotation.observableValue().addListener()!=TChangeListener.class)
			callParser(annotation.observableValue(), object, getComponentDescriptor());
		
	}
		
}
