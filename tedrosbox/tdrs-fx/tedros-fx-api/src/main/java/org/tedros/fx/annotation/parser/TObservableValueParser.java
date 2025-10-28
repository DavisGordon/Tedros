package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.TObservableValue;
import org.tedros.fx.annotation.parser.engine.TAnnotationParser;

import javafx.beans.value.ObservableValue;

@SuppressWarnings("rawtypes")
public class TObservableValueParser extends TAnnotationParser<TObservableValue, ObservableValue>{
	
	@Override
	public void parse(TObservableValue annotation, ObservableValue object, String... byPass) throws Exception {
		super.parse(annotation, object, byPass);
	}
		
}
