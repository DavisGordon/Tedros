/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 18/01/2014
 */
package com.tedros.fxapi.annotation.parser;

import java.lang.annotation.Annotation;

import javafx.scene.image.ImageView;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public final class TImageViewParser extends TAnnotationParser<Annotation, ImageView> {

		@Override
		public void parse(Annotation annotation, ImageView object, String... byPass) throws Exception {
			super.parse(annotation, object, "image");
		}
}
