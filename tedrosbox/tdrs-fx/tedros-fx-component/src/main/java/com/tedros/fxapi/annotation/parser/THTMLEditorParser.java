package com.tedros.fxapi.annotation.parser;

import com.tedros.fxapi.annotation.control.THTMLEditor;

public class THTMLEditorParser extends TAnnotationParser<THTMLEditor, com.tedros.fxapi.control.THTMLEditor> {

	@Override
	public void parse(THTMLEditor ann, com.tedros.fxapi.control.THTMLEditor control, String... byPass)
			throws Exception {
		
		if(!"".equals(ann.html()))
			control.settHtml(ann.html());
		
		control.settShowActionsToolBar(ann.showActionsToolBar());
		
		super.parse(ann, control, "html", "showActionsToolBar");
	}
	
}
