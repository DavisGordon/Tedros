package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.listener.TChangeListener;
import org.tedros.fx.annotation.listener.TInvalidationListener;
import org.tedros.fx.annotation.property.TReadOnlyBooleanProperty;

import javafx.beans.property.ReadOnlyBooleanProperty;

public class TReadOnlyBooleanPropertyParser extends TAnnotationParser<TReadOnlyBooleanProperty, ReadOnlyBooleanProperty>{
	
	@Override
	public void parse(TReadOnlyBooleanProperty annotation, ReadOnlyBooleanProperty object, String... byPass) throws Exception {
		if(annotation.observable().addListener()!=TInvalidationListener.class)
			callParser(annotation.observable(), object, getComponentDescriptor());
		if(annotation.observableValue().addListener()!=TChangeListener.class)
			callParser(annotation.observableValue(), object, getComponentDescriptor());
		
	}
		
}
