package org.tedros.fx.builder;

import java.lang.annotation.Annotation;

import javafx.scene.Node;


/**
 * <pre>
 * A generic builder.
 * 
 * T - the object class to build
 * </pre>
 * */
public interface ITComponentBuilder<T> extends ITBuilder {
	
	public Node build(final Annotation annotation, final T fieldObject) throws Exception;
}
