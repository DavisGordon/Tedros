package com.tedros.fxapi.annotation.parser;

import javafx.scene.control.Slider;

import com.tedros.fxapi.annotation.control.TSliderField;

public class TSliderParser extends TControlFieldParser<TSliderField, Slider>{

	private static TSliderParser instance;
	
	private TSliderParser(){
		
	}
	
	public static TSliderParser getInstance(){
		if(instance==null)
			instance = new TSliderParser();
		return instance;	
	}
}