/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import java.io.File;
import java.lang.annotation.Annotation;

import com.tedros.core.context.TedrosContext;
import com.tedros.fxapi.annotation.control.TDirectoryField;

import javafx.beans.property.Property;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public final class TDirectoryFieldBuilder 
extends TBuilder
implements ITControlBuilder<com.tedros.fxapi.control.TDirectoryField, Property<File>> {

	private static TDirectoryFieldBuilder instance;
	
	private TDirectoryFieldBuilder(){
		
	}
	
	public static TDirectoryFieldBuilder getInstance(){
		if(instance==null)
			instance = new TDirectoryFieldBuilder();
		return instance;
	}
	
	public com.tedros.fxapi.control.TDirectoryField build(final Annotation annotation, final Property<File> directoryProperty) throws Exception {
		TDirectoryField tAnnotation = (TDirectoryField) annotation;
		final com.tedros.fxapi.control.TDirectoryField control = new com.tedros.fxapi.control.TDirectoryField(TedrosContext.getStage());
		callParser(tAnnotation, control);
		control.tFileProperty().bindBidirectional(directoryProperty);
		return control;
	}
		
}
