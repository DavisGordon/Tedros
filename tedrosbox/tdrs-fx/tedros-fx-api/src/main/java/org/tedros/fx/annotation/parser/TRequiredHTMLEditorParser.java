package org.tedros.fx.annotation.parser;

import java.lang.annotation.Annotation;

import org.tedros.fx.control.TRequiredHTMLEditor;

public class TRequiredHTMLEditorParser<A extends Annotation> extends TControlFieldParser<A, TRequiredHTMLEditor>{

	@Override
	public void parse(A annotation, TRequiredHTMLEditor object,
			String... byPass) throws Exception {
	
		super.parse(annotation, object, "+required");
	}
}