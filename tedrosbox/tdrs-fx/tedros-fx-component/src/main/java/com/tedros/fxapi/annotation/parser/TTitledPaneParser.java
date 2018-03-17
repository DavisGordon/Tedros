package com.tedros.fxapi.annotation.parser;

import javafx.scene.control.TitledPane;

import com.tedros.fxapi.annotation.layout.TTitledPane;

public class TTitledPaneParser extends TAnnotationParser<TTitledPane, TitledPane> {

	private static TTitledPaneParser instance;
	
	private TTitledPaneParser(){
		
	}
	
	public static  TTitledPaneParser getInstance(){
		if(instance==null)
			instance = new TTitledPaneParser();
		return instance;	
	}
	
	@Override
	public void parse(TTitledPane annotation, TitledPane object, String... byPass) throws Exception {
		super.parse(annotation, object, "fields", "layoutType");
	}
}
