package com.tedros.fxapi.annotation.parser;

import javafx.scene.control.Control;

import com.tedros.fxapi.annotation.scene.control.TControl;

public class TControlParser extends TAnnotationParser<TControl, Control> {

	private static TControlParser instance;
	
	private TControlParser(){
		
	}
	
	public static TControlParser getInstance(){
		if(instance==null)
			instance = new TControlParser();
		return instance;
		
	}
}
