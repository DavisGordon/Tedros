/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;

import com.tedros.fxapi.annotation.scene.web.TWebView;

import javafx.beans.property.Property;
import javafx.scene.web.WebView;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public final class TWebViewBuilder 
extends TBuilder
implements ITControlBuilder<WebView, Property<String>> {

	public WebView build(final Annotation annotation, final Property<String> attrProperty) throws Exception {
		final TWebView ann = (TWebView) annotation;
		WebView control = new WebView();
		callParser(ann, control);
		return control;
	}
}
