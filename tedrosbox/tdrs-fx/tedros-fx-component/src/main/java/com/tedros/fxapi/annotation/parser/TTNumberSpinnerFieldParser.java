package com.tedros.fxapi.annotation.parser;

import com.tedros.fxapi.annotation.control.TNumberSpinnerField;


public class TTNumberSpinnerFieldParser extends TControlFieldParser<TNumberSpinnerField, com.tedros.fxapi.control.TNumberSpinnerField>{

	private static  TTNumberSpinnerFieldParser instance;
	
	private TTNumberSpinnerFieldParser(){
		
	}
	
	public static TTNumberSpinnerFieldParser getInstance(){
		if(instance==null)
			instance = new TTNumberSpinnerFieldParser();
		return instance;	
	}
}