package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.listener.TChangeListener;
import org.tedros.fx.annotation.listener.TInvalidationListener;
import org.tedros.fx.annotation.parser.engine.TAnnotationParser;
import org.tedros.fx.annotation.property.TReadOnlyIntegerProperty;

import javafx.beans.property.ReadOnlyIntegerProperty;

public class TReadOnlyIntegerPropertyParser extends TAnnotationParser<TReadOnlyIntegerProperty, ReadOnlyIntegerProperty>{
	
	@Override
	public void parse(TReadOnlyIntegerProperty annotation, ReadOnlyIntegerProperty object, String... byPass) throws Exception {
		if(annotation.observable().addListener()!=TInvalidationListener.class)
			callParser(annotation.observable(), object, getComponentDescriptor());
		if(annotation.observableValue().addListener()!=TChangeListener.class)
			callParser(annotation.observableValue(), object, getComponentDescriptor());
		
	}
		
}
