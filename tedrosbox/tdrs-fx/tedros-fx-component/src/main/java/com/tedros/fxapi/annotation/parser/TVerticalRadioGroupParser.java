package com.tedros.fxapi.annotation.parser;

import com.tedros.fxapi.annotation.control.TVerticalRadioGroup;

public class TVerticalRadioGroupParser extends TAnnotationParser<TVerticalRadioGroup, com.tedros.fxapi.control.TVerticalRadioGroup> {

private static TVerticalRadioGroupParser instance;
	
	private TVerticalRadioGroupParser(){
		
	}
	
	public static  TVerticalRadioGroupParser getInstance(){
		if(instance==null)
			instance = new TVerticalRadioGroupParser();
		return instance;	
	}
	
}
