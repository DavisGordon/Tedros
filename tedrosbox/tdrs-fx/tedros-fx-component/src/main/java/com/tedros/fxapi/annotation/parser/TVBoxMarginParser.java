package com.tedros.fxapi.annotation.parser;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import org.apache.commons.lang3.StringUtils;

import com.tedros.fxapi.annotation.layout.TMargin;
import com.tedros.fxapi.annotation.layout.TVBoxMargin;

public class TVBoxMarginParser extends TAnnotationParser<TVBoxMargin, VBox> {

	private static TVBoxMarginParser instance;
	
	private TVBoxMarginParser() {
		
	}
	
	public static TVBoxMarginParser getInstance() {
		if(instance==null)
			instance = new TVBoxMarginParser();
		return instance;
	}

	@Override
	public void parse(TVBoxMargin annotation, VBox object, String... byPass) throws Exception {
		
		if(annotation.margin().length>0){
			TMargin[] arr = annotation.margin();
			for (TMargin tMargin : arr) {
				if(StringUtils.isBlank(tMargin.field()))
					continue;
				if(getComponentDescriptor().getFieldBoxMap().containsKey(tMargin.field())){
					Node node = getComponentDescriptor().getFieldBoxMap().get(tMargin.field());
					VBox.setMargin(node, (Insets) TTypeAnalyserParserDelegate.parse(tMargin.insets(), getComponentDescriptor()));
				}else{
					System.out.println("Warning: TVBoxMarginParser cant find the fieldBox for the field "+tMargin.field()+" at fieldBox list, maybe they was not builded yet!");
				}
					
			}
			
		}
	}
	
}
