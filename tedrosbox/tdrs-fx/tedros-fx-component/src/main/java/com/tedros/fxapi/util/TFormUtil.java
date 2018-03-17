/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 26/02/2014
 */
package com.tedros.fxapi.util;

import javafx.collections.ObservableMap;
import javafx.scene.Node;

import com.tedros.fxapi.control.TBigDecimalField;
import com.tedros.fxapi.control.TBigIntegerField;
import com.tedros.fxapi.control.TCheckBoxField;
import com.tedros.fxapi.control.TComboBoxField;
import com.tedros.fxapi.control.TDatePickerField;
import com.tedros.fxapi.control.TDoubleField;
import com.tedros.fxapi.control.THorizontalRadioGroup;
import com.tedros.fxapi.control.TIntegerField;
import com.tedros.fxapi.control.TLongField;
import com.tedros.fxapi.control.TMaskField;
import com.tedros.fxapi.control.TNumberSpinnerField;
import com.tedros.fxapi.control.TPasswordField;
import com.tedros.fxapi.control.TPickListField;
import com.tedros.fxapi.control.TSlider;
import com.tedros.fxapi.control.TTextAreaField;
import com.tedros.fxapi.control.TTextField;
import com.tedros.fxapi.control.TVerticalRadioGroup;
import com.tedros.fxapi.form.ITFilterForm;
import com.tedros.fxapi.form.ITModelForm;
import com.tedros.fxapi.form.TFieldBox;
import com.tedros.fxapi.presenter.filter.TFilterModelView;
import com.tedros.fxapi.presenter.model.TModelView;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public final class TFormUtil {
	
	private TFormUtil() {
		
	}
	
	public static void cleanForm(final ITModelForm<TModelView<?>> modelViewForm){
		clean(modelViewForm.gettFieldBoxMap());
	}
	
	public static void cleanForm(final ITFilterForm<? extends TFilterModelView> filterViewForm){
		clean(filterViewForm.gettFieldBoxMap());
	}
	
	@SuppressWarnings("rawtypes")
	private static void clean(final ObservableMap<String, TFieldBox> map){
		for(final String key : map.keySet()){
			final TFieldBox fieldBox = map.get(key);
			Node control = fieldBox.gettControl();
			
			// *** TVerticalRadioGroup ***
			
			if (control instanceof TVerticalRadioGroup) {
				TVerticalRadioGroup tControl = (TVerticalRadioGroup) control;
				if(tControl.getSelectedToggle()!=null)
					tControl.getSelectedToggle().setSelected(false);
			}
			
			// *** THorizontalRadioGroup ***
			
			if (control instanceof THorizontalRadioGroup) {
				THorizontalRadioGroup tControl = (THorizontalRadioGroup) control;
				if(tControl.getSelectedToggle()!=null)
					tControl.getSelectedToggle().setSelected(false);
			}
			
			// *** TPickList ***
			
			if (control instanceof TPickListField) {
				((TPickListField)control).getSelectedList().clear();
			}
			
			// *** TTextField ***
			
			if (control instanceof TTextField) {
				((TTextField)control).setText(null);
			}
			// *** TTextField ***
			
			if (control instanceof TMaskField) {
				((TMaskField)control).setText(null);
			}
			
			// *** TBigIntegerField ***
			
			if (control instanceof TBigIntegerField) {
				((TBigIntegerField)control).setText(null);
			}
			
			// *** TBigDecimalField ***
			
			if (control instanceof TBigDecimalField) {
				((TBigDecimalField)control).setText(null);
			}
			
			// *** TDoubleField ***
			
			if (control instanceof TDoubleField) {
				((TDoubleField)control).setText(null);
			}
			
			// *** TLongField ***
			
			if (control instanceof TLongField) {
				((TLongField)control).setText(null);
			}
			
			// *** TIntegerField ***
			
			if (control instanceof TIntegerField) {
				((TIntegerField)control).setText(null);
			}
			
			// *** TTextArea ***
			
			if (control instanceof TTextAreaField) {
				((TTextAreaField)control).setText(null);
			}
			
			// *** TPasswordField ***
			
			if (control instanceof TPasswordField) {
				((TPasswordField)control).setText(null);
			}
			
			// *** TNumberSpinner ***
			
			if (control instanceof TNumberSpinnerField) {
				((TNumberSpinnerField)control).setText(null);
			}
			
			// *** TCheckBox ***
			
			if (control instanceof TCheckBoxField) {
				((TCheckBoxField)control).setSelected(false);
			}
			
			// *** TComboBoxField ***
			
			if (control instanceof TComboBoxField) {
				((TComboBoxField)control).getSelectionModel().clearSelection();
			}
			
			// *** TSlider ***
			
			if (control instanceof TSlider) {
				((TSlider)control).setValue(0);
			}
			
			// *** TDatePicker ***
			
			if (control instanceof TDatePickerField) {
				((TDatePickerField)control).setValue(null);
			}
			
		}
	}

}
