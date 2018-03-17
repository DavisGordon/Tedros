package com.tedros.fxapi.annotation.parser;

import javafx.scene.control.ButtonBase;

import com.tedros.fxapi.annotation.scene.control.TButtonBase;

public class TButtonBaseParser extends TAnnotationParser<TButtonBase, ButtonBase> {
	
	private static TButtonBaseParser instance;
	
	private TButtonBaseParser(){
		
	}
	
	public static  TButtonBaseParser getInstance(){
		if(instance==null)
			instance = new TButtonBaseParser();
		return instance;	
	}
}
