/**
 * 
 */
package com.tedros.fxapi.annotation.control;

import javafx.scene.control.TableCell;
import javafx.util.Callback;

/**
 * @author Davis Gordon
 *
 */
public @interface TCellCallbackFactory {

	/*public @interface TCheckBoxTableCell{
		public String forTableColumn() default "";
	}
	
	public @interface TChoiceBoxTableCell{
		public String forTableColumn() default "";
	}
	
	public @interface TComboBoxTableCell{
		public String forTableColumn() default "";
	}
	
	public @interface TProgressBarTableCell{
		public String forTableColumn() default "";
	}
	
	public @interface TTextFieldTableCell{
		public String forTableColumn() default "";
	}
	
	public boolean parse();
	
	public TCheckBoxTableCell checkBoxTableCell() default @TCheckBoxTableCell();
	
	public TChoiceBoxTableCell choiceBoxTableCell() default @TChoiceBoxTableCell();
	
	public TComboBoxTableCell comboBoxTableCell() default @TComboBoxTableCell();
	
	public TProgressBarTableCell progressBarTableCell() default @TProgressBarTableCell();
	
	public TTextFieldTableCell textFieldTableCell() default @TTextFieldTableCell();*/
	
	public boolean parse();
	
	@SuppressWarnings("rawtypes")
	public Class<? extends Callback> callBack() default Callback.class;
	
	@SuppressWarnings("rawtypes")
	public Class<? extends TableCell> tableCell() default TableCell.class;
	
}
