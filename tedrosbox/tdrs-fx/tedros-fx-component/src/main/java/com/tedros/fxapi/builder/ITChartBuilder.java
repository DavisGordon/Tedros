package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;

import javafx.scene.Node;

public interface ITChartBuilder<C extends Node> extends ITFieldBuilder {
	
	public C build(final Annotation tAnnotation, C chartField) throws Exception;

}
