package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;

import com.tedros.fxapi.reader.THtmlReader;

public interface ITReaderHtmlBuilder<A extends Annotation, T> extends ITFieldBuilder {
	
	public THtmlReader build(final A annotation, final T fieldType) throws Exception;
	

}