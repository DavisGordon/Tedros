package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.listener.TChangeListener;
import org.tedros.fx.annotation.listener.TInvalidationListener;
import org.tedros.fx.annotation.parser.engine.TAnnotationParser;
import org.tedros.fx.annotation.property.TReadOnlyDoubleProperty;

import javafx.beans.property.ReadOnlyDoubleProperty;

public class TReadOnlyDoublePropertyParser extends TAnnotationParser<TReadOnlyDoubleProperty, ReadOnlyDoubleProperty>{
	
	
	@Override
	public void parse(TReadOnlyDoubleProperty annotation, ReadOnlyDoubleProperty object, String... byPass) throws Exception {
		if(annotation.observable().addListener()!=TInvalidationListener.class)
			callParser(annotation.observable(), object, getComponentDescriptor());
		if(annotation.observableValue().addListener()!=TChangeListener.class)
			callParser(annotation.observableValue(), object, getComponentDescriptor());
		
	}
		
}
