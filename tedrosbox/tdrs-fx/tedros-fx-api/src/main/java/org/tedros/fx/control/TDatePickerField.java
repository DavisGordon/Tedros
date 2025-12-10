/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 26/11/2013
 */
package org.tedros.fx.control;

import java.util.Date;
import java.util.Locale;

/**
 *  
 *
 * @author Davis Gordon
 *
 */
public class TDatePickerField extends TRequiredDatePicker {

	public TDatePickerField() {
		super();
		super.tRequiredNodeProperty().setValue(this);
	}
	
	public TDatePickerField(Locale locale) {
		super(locale);
		super.tRequiredNodeProperty().setValue(this);
    }
	
	public TDatePickerField(final Date date) {
    	super(date);
    	super.tRequiredNodeProperty().setValue(this);
    }
	
}
