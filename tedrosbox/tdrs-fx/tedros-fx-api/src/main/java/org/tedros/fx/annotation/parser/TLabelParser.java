/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 18/01/2014
 */
package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.control.TLabel;

import javafx.scene.control.Labeled;

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
