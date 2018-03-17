package com.tedros.fxapi.annotation.parser;

import com.tedros.fxapi.annotation.control.TDatePickerField;
import com.tedros.fxapi.control.DatePicker;

public class TDatePickerParser extends TAnnotationParser<TDatePickerField, DatePicker> {

	private static TDatePickerParser instance;

	private TDatePickerParser(){
		
	}
	
	public static TDatePickerParser getInstance(){
		if(instance==null)
			instance = new TDatePickerParser();
		return instance;
	}
}
