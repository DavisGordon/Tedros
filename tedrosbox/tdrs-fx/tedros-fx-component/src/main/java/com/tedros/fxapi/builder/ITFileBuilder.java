package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;

import com.tedros.fxapi.property.TSimpleFileEntityProperty;
import com.tedros.fxapi.property.TSimpleFileModelProperty;

import javafx.beans.property.Property;
import javafx.scene.Node;

public interface ITFileBuilder <C extends Node> extends ITFieldBuilder {
	
	public C build(final Annotation annotation, final Property<String> fileNameProperty, final Property<byte[]> byteArrayProperty) throws Exception;
	
	public C build(final Annotation annotation, final TSimpleFileModelProperty<?> tSimpleFileModelProperty) throws Exception;
	
	public C build(final Annotation annotation, final TSimpleFileEntityProperty<?> tSimpleFileEntityProperty) throws Exception;

}
