package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.listener.TChangeListener;
import org.tedros.fx.annotation.listener.TInvalidationListener;
import org.tedros.fx.annotation.property.TReadOnlyStringProperty;

import javafx.beans.property.ReadOnlyStringProperty;

public class TReadOnlyStringPropertyParser extends TAnnotationParser<TReadOnlyStringProperty, ReadOnlyStringProperty>{
	
	@Override
	public void parse(TReadOnlyStringProperty annotation, ReadOnlyStringProperty object, String... byPass) throws Exception {
		if(annotation.observable().addListener()!=TInvalidationListener.class)
			callParser(annotation.observable(), object, getComponentDescriptor());
		if(annotation.observableValue().addListener()!=TChangeListener.class)
			callParser(annotation.observableValue(), object, getComponentDescriptor());
		
	}
		
}
