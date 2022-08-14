package org.tedros.tools.annotation.parser;

import org.tedros.fx.annotation.parser.TAnnotationParser;
import org.tedros.tools.annotation.TNotifyLink;

import javafx.scene.control.Hyperlink;

public class TNotifyLinkParser extends TAnnotationParser<TNotifyLink, Hyperlink> {

	@Override
	public void parse(TNotifyLink annotation, Hyperlink object, String... byPass) throws Exception {
		super.parse(annotation, object, "entityBuilder");
	}
	
}
