/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;

import org.tedros.fx.annotation.control.TScrollPane;
import org.tedros.fx.reader.THtmlReader;

import javafx.scene.control.ScrollPane;


/**
 * A builder to {@link org.tedros.fx.control.TNumberSpinnerField}
 *
 * @author Davis Gordon
 *
 */
public final class TScrollPaneBuilder
extends TBuilder
implements ITLayoutBuilder<ScrollPane> {

	@Override
	public ScrollPane build(Annotation annotation) throws Exception {
		final TScrollPane tAnnotation = (TScrollPane) annotation;
		final ScrollPane control = new ScrollPane();
		callParser(tAnnotation, control);
		return control;
	}

	@Override
	public THtmlReader build(Annotation tAnnotation, THtmlReader tHtmlReader) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
