package com.tedros.fxapi.annotation.parser;

import java.lang.annotation.Annotation;

import com.tedros.fxapi.control.TRequiredHTMLEditor;

public class TRequiredHTMLEditorParser<A extends Annotation> extends TControlFieldParser<A, TRequiredHTMLEditor>{

	@Override
	public void parse(A annotation, TRequiredHTMLEditor object,
			String... byPass) throws Exception {
	
		super.parse(annotation, object, "+required");
	}
}