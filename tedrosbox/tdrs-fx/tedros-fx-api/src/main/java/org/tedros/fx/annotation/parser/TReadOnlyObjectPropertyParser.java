package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.listener.TChangeListener;
import org.tedros.fx.annotation.listener.TInvalidationListener;
import org.tedros.fx.annotation.property.TReadOnlyObjectProperty;

import javafx.beans.property.ReadOnlyObjectProperty;

@SuppressWarnings("rawtypes")
public class TReadOnlyObjectPropertyParser extends TAnnotationParser<TReadOnlyObjectProperty, ReadOnlyObjectProperty>{
	
	@Override
	public void parse(TReadOnlyObjectProperty annotation, ReadOnlyObjectProperty object, String... byPass) throws Exception {
		if(annotation.observable().addListener()!=TInvalidationListener.class)
			callParser(annotation.observable(), object, getComponentDescriptor());
		if(annotation.observableValue().addListener()!=TChangeListener.class)
			callParser(annotation.observableValue(), object, getComponentDescriptor());
		
	}
		
}
