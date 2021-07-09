package com.tedros.fxapi.annotation.parser;

import java.lang.annotation.Annotation;

import javafx.scene.layout.HBox;

public class THBoxParser extends TAnnotationParser<Annotation, HBox> {

	private static THBoxParser instance;
	
	private THBoxParser(){
		
	}
	
	public static  THBoxParser getInstance(){
		if(instance==null)
			instance = new THBoxParser();
		return instance;	
	}
}
