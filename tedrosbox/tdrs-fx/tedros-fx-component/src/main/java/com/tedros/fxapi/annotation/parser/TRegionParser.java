package com.tedros.fxapi.annotation.parser;

import javafx.scene.layout.Region;

import com.tedros.fxapi.annotation.scene.layout.TRegion;

public class TRegionParser extends TAnnotationParser<TRegion, Region>{
	
	private static TRegionParser instance;
	
	private TRegionParser(){
		
	}
	
	public static TRegionParser getInstance(){
		if(instance==null)
			instance = new TRegionParser();
		return instance;
		
	}
		
}
