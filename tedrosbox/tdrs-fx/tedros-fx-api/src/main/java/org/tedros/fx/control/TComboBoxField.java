/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 02/01/2014
 */
package org.tedros.fx.control;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TComboBoxField<T extends Object> extends TRequiredComboBox<T> {

	/**
	 * 
	 */
	public TComboBoxField() {
		super();
		super.tRequiredNodeProperty().setValue(this);
	}
	
}
