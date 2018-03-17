package com.tedros.fxapi.annotation.parser;

import javafx.scene.control.Tab;

import com.tedros.fxapi.annotation.control.TTab;


public class TTabParser extends TAnnotationParser<TTab, Tab>{

	private static  TTabParser instance;
	
	private TTabParser(){
		
	}
	
	public static  TTabParser getInstance(){
		if(instance==null)
			instance = new TTabParser();
		return instance;	
	}
	
	@Override
	public void parse(TTab annotation, Tab object, String... byPass) throws Exception {
		super.parse(annotation, object, "content");
	}
}