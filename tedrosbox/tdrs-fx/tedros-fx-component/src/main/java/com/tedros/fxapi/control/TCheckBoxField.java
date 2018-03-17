/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 03/12/2013
 */
package com.tedros.fxapi.control;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TCheckBoxField extends TRequiredCheckBox {
	
	
	public TCheckBoxField() {
		getStyleClass().addAll("box-input","t-form-control-label");
	}
	
	public TCheckBoxField(String text) {
		super(text);
		getStyleClass().addAll("box-input","t-form-control-label");
	}
	
}
