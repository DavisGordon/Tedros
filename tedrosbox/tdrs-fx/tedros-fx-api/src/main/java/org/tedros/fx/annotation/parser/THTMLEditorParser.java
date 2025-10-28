package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.control.THTMLEditor;
import org.tedros.fx.annotation.parser.engine.TAnnotationParser;

public class THTMLEditorParser extends TAnnotationParser<THTMLEditor, org.tedros.fx.control.THTMLEditor> {

	@Override
	public void parse(THTMLEditor ann, org.tedros.fx.control.THTMLEditor control, String... byPass)
			throws Exception {
		
		if(!"".equals(ann.html()))
			control.settHtml(ann.html());
		
		control.settShowActionsToolBar(ann.showActionsToolBar());
		
		super.parse(ann, control, "html", "showActionsToolBar");
	}
	
}
