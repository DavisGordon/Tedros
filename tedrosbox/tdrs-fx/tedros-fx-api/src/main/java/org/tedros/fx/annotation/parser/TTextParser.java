/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 18/01/2014
 */
package org.tedros.fx.annotation.parser;

import java.lang.annotation.Annotation;

import org.tedros.core.TLanguage;
import org.tedros.fx.control.TText;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public final class TTextParser extends TAnnotationParser<Annotation, TText> {

		@Override
		public void parse(Annotation annotation, TText control, String... byPass) throws Exception {
			org.tedros.fx.annotation.text.TText tAnnotation = (org.tedros.fx.annotation.text.TText) annotation;
			control.settTextStyle(tAnnotation.textStyle());
			control.setText(TLanguage.getInstance(null).getString(tAnnotation.text()));
			
			super.parse(annotation, control, "textStyle", "text", "mode");
		}
}
