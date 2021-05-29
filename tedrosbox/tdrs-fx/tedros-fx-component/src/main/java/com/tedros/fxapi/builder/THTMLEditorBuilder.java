/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;

import com.tedros.fxapi.annotation.control.THTMLEditor;

import javafx.beans.property.SimpleStringProperty;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public final class THTMLEditorBuilder 
extends TBuilder
implements ITControlBuilder<com.tedros.fxapi.control.THTMLEditor, SimpleStringProperty> {
	
	public com.tedros.fxapi.control.THTMLEditor build(final Annotation annotation, final SimpleStringProperty attrProperty) throws Exception {
	
		final THTMLEditor tAnnotation = (THTMLEditor) annotation;
		final com.tedros.fxapi.control.THTMLEditor control = new com.tedros.fxapi.control.THTMLEditor(attrProperty);
        
		
		callParser(tAnnotation, control);
		
		return control;
	}
}
