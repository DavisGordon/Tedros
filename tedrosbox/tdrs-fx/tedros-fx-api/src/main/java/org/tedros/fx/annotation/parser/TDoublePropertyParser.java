package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.listener.TChangeListener;
import org.tedros.fx.annotation.listener.TInvalidationListener;
import org.tedros.fx.annotation.property.TDoubleProperty;

import javafx.beans.property.DoubleProperty;

public class TDoublePropertyParser extends TAnnotationParser<TDoubleProperty, DoubleProperty>{
	
	
	
	@Override
	public void parse(TDoubleProperty annotation, DoubleProperty object, String... byPass) throws Exception {
		if(annotation.observable().addListener()!=TInvalidationListener.class)
			callParser(annotation.observable(), object, getComponentDescriptor());
		if(annotation.observableValue().addListener()!=TChangeListener.class)
			callParser(annotation.observableValue(), object, getComponentDescriptor());
		
	}
		
}
