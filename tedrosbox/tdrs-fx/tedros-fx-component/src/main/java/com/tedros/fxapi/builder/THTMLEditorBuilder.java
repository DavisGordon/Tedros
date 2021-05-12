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
import javafx.scene.web.HTMLEditor;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public final class THTMLEditorBuilder 
extends TBuilder
implements ITControlBuilder<HTMLEditor, SimpleStringProperty> {
	
	public HTMLEditor build(final Annotation annotation, final SimpleStringProperty attrProperty) throws Exception {
	
		final THTMLEditor tAnnotation = (THTMLEditor) annotation;
		final HTMLEditor control = new HTMLEditor();
        
		if(attrProperty.getValue()!=null)
			control.setHtmlText(attrProperty.getValue());
		//control.
		callParser(tAnnotation, control);
		
		if(attrProperty.getValue()!=null)
			control.setHtmlText(attrProperty.getValue());
		
		return control;
	}
}
