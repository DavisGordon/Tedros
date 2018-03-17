package com.tedros.fxapi.annotation.parser;

import javafx.scene.control.TextArea;

import com.tedros.fxapi.annotation.control.TTextAreaField;

public class TTextAreaParser extends TAnnotationParser<TTextAreaField, TextArea> {
	
	private static TTextAreaParser instance;
	
	private TTextAreaParser(){
		
	}
	
	public static  TTextAreaParser getInstance(){
		if(instance==null)
			instance = new TTextAreaParser();
		return instance;	
	}
}
