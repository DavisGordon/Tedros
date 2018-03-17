package com.tedros.fxapi.annotation.parser;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

import org.apache.commons.lang3.StringUtils;

import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.form.TFieldBox;

public class THGrowParser extends TAnnotationParser<THGrow, HBox> {

	private static THGrowParser instance;
	
	private THGrowParser() {
		
	}
	
	public static THGrowParser getInstance() {
		if(instance==null)
			instance = new THGrowParser();
		return instance;
	}

	@Override
	public void parse(THGrow annotation, HBox object, String... byPass) throws Exception {
		
		if(annotation.priority().length>0){
			TPriority[] arr = annotation.priority();
			for (TPriority tPriority : arr) {
				if(StringUtils.isBlank(tPriority.field()))
					continue;
				for(Node fb : object.getChildren()){
					if(fb instanceof TFieldBox){
						TFieldBox f = (TFieldBox) fb;
						if(tPriority.field().equals(f.gettControlFieldName()))
							HBox.setHgrow(fb, tPriority.priority());
					}
				}	
			}
			
		}
	}
	
}
