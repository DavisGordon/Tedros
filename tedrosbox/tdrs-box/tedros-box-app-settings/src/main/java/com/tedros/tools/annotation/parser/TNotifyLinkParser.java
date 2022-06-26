package com.tedros.tools.annotation.parser;

import com.tedros.fxapi.annotation.parser.TAnnotationParser;
import com.tedros.tools.annotation.TNotifyLink;

import javafx.scene.control.Hyperlink;

public class TNotifyLinkParser extends TAnnotationParser<TNotifyLink, Hyperlink> {

	@Override
	public void parse(TNotifyLink annotation, Hyperlink object, String... byPass) throws Exception {
		super.parse(annotation, object, "entityBuilder");
	}
	
}
