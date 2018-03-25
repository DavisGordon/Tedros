/**
 * 
 */
package com.tedros.fxapi.annotation.control;

import javafx.scene.control.TableCell;

/**
 * @author Davis Gordon
 *
 */
public @interface TCellFactory {
	
	public boolean parse();
	
	public TCallbackFactory callBack() default @TCallbackFactory(parse=false);
	
	@SuppressWarnings("rawtypes")
	public Class<? extends TableCell> tableCell() default TableCell.class;
	
}
