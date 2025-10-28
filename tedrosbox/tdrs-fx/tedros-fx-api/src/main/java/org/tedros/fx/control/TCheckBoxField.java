/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 03/12/2013
 */
package org.tedros.fx.control;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TCheckBoxField extends TRequiredCheckBox {
	
	
	public TCheckBoxField() {
		getStyleClass().addAll("box-input","t-form-control-label");
		super.tRequiredNodeProperty().setValue(this);
	}
	
	public TCheckBoxField(String text) {
		super(text);
		getStyleClass().addAll("box-input","t-form-control-label");
		super.tRequiredNodeProperty().setValue(this);
	}
	
}
