package com.tedros.fxapi.annotation.parser;

import javafx.beans.Observable;

import com.tedros.fxapi.annotation.TObservable;

public class TObservableParser extends TAnnotationParser<TObservable, Observable>{
	
	
	@Override
	public void parse(TObservable annotation, Observable object, String... byPass) throws Exception {
		super.parse(annotation, object, byPass);
	}
		
}
