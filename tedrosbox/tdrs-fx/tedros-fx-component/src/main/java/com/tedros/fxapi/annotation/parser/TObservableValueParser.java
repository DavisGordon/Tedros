package com.tedros.fxapi.annotation.parser;

import javafx.beans.value.ObservableValue;

import com.tedros.fxapi.annotation.TObservableValue;

@SuppressWarnings("rawtypes")
public class TObservableValueParser extends TAnnotationParser<TObservableValue, ObservableValue>{
	
	@Override
	public void parse(TObservableValue annotation, ObservableValue object, String... byPass) throws Exception {
		super.parse(annotation, object, byPass);
	}
		
}
