/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 18/01/2014
 */
package com.tedros.fxapi.annotation.parser;

import java.lang.annotation.Annotation;

import com.tedros.fxapi.control.TText;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public final class TTextParser extends TAnnotationParser<Annotation, TText> {

		@Override
		public void parse(Annotation annotation, TText object, String... byPass) throws Exception {
			com.tedros.fxapi.annotation.text.TText tAnnotation = (com.tedros.fxapi.annotation.text.TText) annotation;
			object.settTextStyle(tAnnotation.textStyle());
			super.parse(annotation, object, "textStyle");
		}
}
