/**
 * 
 */
package com.tedros.fxapi.annotation.control;

import javafx.util.Callback;

/**
 * @author Davis Gordon
 *
 */
public @interface TCallbackFactory {
	
	public boolean parse();
	
	@SuppressWarnings("rawtypes")
	public Class<? extends Callback> value() default Callback.class;
	
}
