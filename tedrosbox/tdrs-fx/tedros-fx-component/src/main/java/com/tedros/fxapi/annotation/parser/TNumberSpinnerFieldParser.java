package com.tedros.fxapi.annotation.parser;

import com.tedros.fxapi.annotation.control.TNumberSpinnerField;


public class TNumberSpinnerFieldParser extends TControlFieldParser<TNumberSpinnerField, com.tedros.fxapi.control.NumberSpinner>{

	private static  TNumberSpinnerFieldParser instance;
	
	private TNumberSpinnerFieldParser(){
		
	}
	
	public static TNumberSpinnerFieldParser getInstance(){
		if(instance==null)
			instance = new TNumberSpinnerFieldParser();
		return instance;	
	}
}