package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;

import javafx.scene.Node;

import com.tedros.fxapi.form.TFieldBox;
import com.tedros.fxapi.reader.THtmlReader;

/**
 * <pre>
 * An interface to build a layout component, most used 
 * to define a builder in layout component annotation.
 * </pre>
 * @author Davis Gordon
 * */
public interface ITLayoutBuilder<C extends Node> extends ITFieldBuilder {
	
	public C build(final Annotation tAnnotation, TFieldBox fieldBox) throws Exception;
	
	public THtmlReader build(final Annotation tAnnotation, THtmlReader tHtmlReader) throws Exception;

}
