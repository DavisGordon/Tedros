package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;

import javafx.scene.Node;

public interface ITReaderBuilder<N extends Node, P> extends ITFieldBuilder {
	
	public N build(final Annotation annotation, final P property) throws Exception;
	
	

}