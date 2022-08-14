package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.TObservable;

import javafx.beans.Observable;

public class TObservableParser extends TAnnotationParser<TObservable, Observable>{
	
	
	@Override
	public void parse(TObservable annotation, Observable object, String... byPass) throws Exception {
		super.parse(annotation, object, byPass);
	}
		
}
