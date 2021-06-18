/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 18/01/2014
 */
package com.tedros.fxapi.annotation.parser;

import javafx.scene.control.Labeled;

import com.tedros.fxapi.annotation.control.TLabel;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public final class TLabelParser extends TAnnotationParser<TLabel, Labeled>{
	
	@Override
	public void parse(TLabel annotation, Labeled object, String... byPass) throws Exception {
		//object.setText(annotation.text());
		super.parse(annotation, object, byPass);
	}
}
