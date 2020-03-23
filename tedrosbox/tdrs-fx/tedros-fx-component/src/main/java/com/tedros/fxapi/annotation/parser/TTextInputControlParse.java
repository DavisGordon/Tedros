package com.tedros.fxapi.annotation.parser;

import javafx.scene.control.TextInputControl;

import com.tedros.fxapi.annotation.control.TTextInputControl;

public class TTextInputControlParse extends TAnnotationParser<TTextInputControl, TextInputControl>{

	private static TTextInputControlParse instance;
	
	private TTextInputControlParse(){
		
	}
	
	public static TTextInputControlParse getInstance(){
		if(instance==null)
			instance = new TTextInputControlParse();
		return instance;	
	}
}
