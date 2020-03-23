package com.tedros.fxapi.annotation.parser;

import java.lang.annotation.Annotation;

import javafx.collections.ObservableList;
import javafx.scene.Node;

import com.tedros.fxapi.builder.ITFieldBuilder;
import com.tedros.fxapi.property.TSimpleFileModelProperty;

public interface ITListFileFieldBuilder<C extends Node> extends ITFieldBuilder {

	public C build(final Annotation annotation, final ObservableList<TSimpleFileModelProperty<?>> fileModelPropertyList) throws Exception;
}
