package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;

import com.tedros.fxapi.reader.THtmlReader;

import javafx.scene.Node;

/**
 * <pre>
 * An interface to build a layout component, most used 
 * to define a builder in layout component annotation.
 * </pre>
 * @author Davis Gordon
 * */
public interface ITLayoutBuilder<C extends Node> extends ITFieldBuilder {
	
	public C build(final Annotation tAnnotation) throws Exception;
	
	public THtmlReader build(final Annotation tAnnotation, THtmlReader tHtmlReader) throws Exception;

}
