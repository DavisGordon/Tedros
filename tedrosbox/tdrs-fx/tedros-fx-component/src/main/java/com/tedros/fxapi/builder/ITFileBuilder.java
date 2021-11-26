package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;

import com.tedros.fxapi.property.TSimpleFileProperty;

import javafx.beans.property.Property;
import javafx.scene.Node;

public interface ITFileBuilder <C extends Node> extends ITFieldBuilder {
	
	public C build(final Annotation annotation, final Property<String> fileNameProperty, final Property<byte[]> byteArrayProperty) throws Exception;
	
	public C build(final Annotation annotation, final TSimpleFileProperty<?> tSimpleFileModelProperty) throws Exception;
	

}
