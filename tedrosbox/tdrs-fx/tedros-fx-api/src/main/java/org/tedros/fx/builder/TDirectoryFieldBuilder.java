/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.io.File;
import java.lang.annotation.Annotation;

import org.tedros.core.context.TedrosContext;
import org.tedros.fx.annotation.control.TDirectoryField;

import javafx.beans.property.Property;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public final class TDirectoryFieldBuilder 
extends TBuilder
implements ITControlBuilder<org.tedros.fx.control.TDirectoryField, Property<File>> {

	public org.tedros.fx.control.TDirectoryField build(final Annotation annotation, final Property<File> directoryProperty) throws Exception {
		TDirectoryField tAnnotation = (TDirectoryField) annotation;
		final org.tedros.fx.control.TDirectoryField control = 
				new org.tedros.fx.control.TDirectoryField(TedrosContext.getStage());
		callParser(tAnnotation, control);
		control.tFileProperty().bindBidirectional(directoryProperty);
		return control;
	}
		
}
