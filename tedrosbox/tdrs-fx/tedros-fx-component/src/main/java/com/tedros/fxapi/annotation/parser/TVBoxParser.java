package com.tedros.fxapi.annotation.parser;

import java.lang.annotation.Annotation;

import javafx.scene.layout.VBox;

public class TVBoxParser extends TAnnotationParser<Annotation, VBox> {

	private static TVBoxParser instance;
	
	private TVBoxParser(){
		
	}
	
	public static  TVBoxParser getInstance(){
		if(instance==null)
			instance = new TVBoxParser();
		return instance;	
	}
}
