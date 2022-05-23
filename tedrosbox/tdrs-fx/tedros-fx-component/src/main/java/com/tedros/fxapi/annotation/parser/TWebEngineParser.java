package com.tedros.fxapi.annotation.parser;

import org.apache.commons.lang3.StringUtils;

import com.tedros.fxapi.annotation.scene.web.TWebEngine;
import com.tedros.util.TedrosFolder;

import javafx.scene.web.WebEngine;

public class TWebEngineParser extends TAnnotationParser<TWebEngine, WebEngine> {

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.annotation.parser.TAnnotationParser#parse(java.lang.annotation.Annotation, java.lang.Object, java.lang.String[])
	 */
	@Override
	public void parse(TWebEngine ann, WebEngine w, String... byPass) throws Exception {
		
		if(StringUtils.isNotBlank(ann.load())) {
			String url = ann.load();
			if(url.contains(TWebEngine.MODULE_FOLDER))
				url = url.replace(TedrosFolder.MODULE_FOLDER.name(), TedrosFolder.MODULE_FOLDER.getFullPath());
			w.load(url);
		}else 
		if(StringUtils.isNotBlank(ann.loadContent())) {
			if(StringUtils.isNotBlank(ann.contentType())) 
				w.loadContent(ann.loadContent(), ann.contentType());
			else
				w.loadContent(ann.loadContent());
		}
		
		if(StringUtils.isNotBlank(ann.userStyleSheetLocation()))
			w.setUserStyleSheetLocation(ann.userStyleSheetLocation());
			
		if(StringUtils.isNotBlank(ann.userAgent()))
			w.setUserAgent(ann.userAgent());
		
		super.parse(ann, w, "load", "loadContent","contentType", "userStyleSheetLocation", "userAgent");
	
	}

}
