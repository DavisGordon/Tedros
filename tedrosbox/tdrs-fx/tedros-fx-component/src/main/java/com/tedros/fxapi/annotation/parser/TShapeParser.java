package com.tedros.fxapi.annotation.parser;

import javafx.scene.shape.Shape;

import com.tedros.fxapi.annotation.scene.shape.TShape;

public class TShapeParser extends TAnnotationParser<TShape, Shape>{
	
	private static TShapeParser instance;
	
	private TShapeParser(){
		
	}
	
	public static TShapeParser getInstance(){
		if(instance==null)
			instance = new TShapeParser();
		return instance;
		
	}
		
}
