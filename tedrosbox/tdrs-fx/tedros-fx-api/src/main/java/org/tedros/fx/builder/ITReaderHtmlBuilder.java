package org.tedros.fx.builder;

import java.lang.annotation.Annotation;

import org.tedros.fx.reader.THtmlReader;

public interface ITReaderHtmlBuilder<A extends Annotation, T> extends ITFieldBuilder {
	
	public THtmlReader build(final A annotation, final T fieldType) throws Exception;
	

}