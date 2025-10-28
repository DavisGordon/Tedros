/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;

import org.tedros.fx.annotation.control.THTMLEditor;

import javafx.beans.property.SimpleStringProperty;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public final class THTMLEditorBuilder 
extends TBuilder
implements ITControlBuilder<org.tedros.fx.control.THTMLEditor, SimpleStringProperty> {
	
	public org.tedros.fx.control.THTMLEditor build(final Annotation annotation, final SimpleStringProperty attrProperty) throws Exception {
	
		final THTMLEditor tAnnotation = (THTMLEditor) annotation;
		final org.tedros.fx.control.THTMLEditor control = 
				new org.tedros.fx.control.THTMLEditor();
		control.settHtml(attrProperty.getValue());
        control.tHTMLProperty().bindBidirectional(attrProperty);
		
		callParser(tAnnotation, control);
		
		return control;
	}
}
