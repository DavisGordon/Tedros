package com.tedros.fxapi.annotation.parser;

import java.lang.annotation.Annotation;

import javafx.scene.layout.StackPane;

public class TStackPaneParser extends TAnnotationParser<Annotation, StackPane> {

	private static TStackPaneParser instance;
	
	private TStackPaneParser(){
		
	}
	
	public static  TStackPaneParser getInstance(){
		if(instance==null)
			instance = new TStackPaneParser();
		return instance;	
	}
}
