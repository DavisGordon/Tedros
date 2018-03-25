/**
 * 
 */
package com.tedros.fxapi.annotation.control;

/**
 * @author Davis Gordon
 *
 */
public @interface TCellValueFactory {
	
	public boolean parse();
	
	public TCallbackFactory value() default @TCallbackFactory(parse=false);
	
}
