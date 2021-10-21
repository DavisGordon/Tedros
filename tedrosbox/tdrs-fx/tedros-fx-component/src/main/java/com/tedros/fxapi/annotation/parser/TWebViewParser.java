package com.tedros.fxapi.annotation.parser;

import com.tedros.fxapi.annotation.scene.web.TWebView;
import com.tedros.fxapi.descriptor.TComponentDescriptor;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class TWebViewParser extends TAnnotationParser<TWebView, WebView> {

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.annotation.parser.TAnnotationParser#parse(java.lang.annotation.Annotation, java.lang.Object, java.lang.String[])
	 */
	@Override
	public void parse(TWebView ann, WebView w, String... byPass) throws Exception {
		
		if(ann.minHeight()>0)
			w.setMinHeight(ann.minHeight());
		
		if(ann.minWidth()>0)
			w.setMinWidth(ann.minWidth());
		
		if(ann.maxHeight()>0)
			w.setMaxHeight(ann.maxHeight());
		
		if(ann.maxWidth()>0)
			w.setMaxWidth(ann.maxWidth());
		
		if(ann.prefWidth()>0)
			w.setPrefWidth(ann.prefWidth());
		
		if(ann.prefHeight()>0)
			w.setPrefHeight(ann.prefHeight());
		
		if(ann.zoom()>0)
			w.setZoom(ann.zoom());
		
		super.parse(ann, w, "engine", "minHeight","minWidth", "maxHeight", "maxWidth", "prefWidth", "prefHeight", "zoom");
	
		WebEngine e = w.getEngine();
		TWebEngineParser p = new TWebEngineParser();
		p.setComponentDescriptor(new TComponentDescriptor(super.getComponentDescriptor(), null));
		p.parse(ann.engine(), e);
	
	}

}
