/**
 * 
 */
package com.tedros.fxapi.annotation.control;


import javafx.scene.control.ListCell;
import javafx.scene.control.TableCell;
import javafx.util.StringConverter;

/**
 * @author Davis Gordon
 *
 */
public @interface TCellFactory {
	
	public boolean parse();
	
	public TCallbackFactory callBack() default @TCallbackFactory(parse=false);
	
	@SuppressWarnings("rawtypes")
	public Class<? extends TableCell> tableCell() default TableCell.class;
	
	@SuppressWarnings("rawtypes")
	public Class<? extends ListCell> listCell() default ListCell.class;
	
	public Class<? extends StringConverter> stringConverter() default StringConverter.class;
	
}
