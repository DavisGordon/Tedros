package com.tedros.fxapi.annotation.parser;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

import org.apache.commons.lang3.StringUtils;

import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.layout.TVGrow;
import com.tedros.fxapi.form.TFieldBox;

public class TVGrowParser extends TAnnotationParser<TVGrow, VBox> {

	private static TVGrowParser instance;
	
	private TVGrowParser() {
		
	}
	
	public static TVGrowParser getInstance() {
		if(instance==null)
			instance = new TVGrowParser();
		return instance;
	}

	@Override
	public void parse(TVGrow annotation, VBox object, String... byPass) throws Exception {
		
		if(annotation.priority().length>0){
			TPriority[] arr = annotation.priority();
			for (TPriority tPriority : arr) {
				if(StringUtils.isBlank(tPriority.field()))
					continue;
				for(Node fb : object.getChildren()){
					if(fb instanceof TFieldBox){
						TFieldBox f = (TFieldBox) fb;
						if(tPriority.field().equals(f.gettControlFieldName()))
							VBox.setVgrow(fb, tPriority.priority());
					}
				}	
			}
			
		}
		
		/*if(annotation.priority().length>0){
			TPriority[] arr = annotation.priority();
			for (TPriority tPriority : arr) {
				if(StringUtils.isBlank(tPriority.field()))
					continue;
				if(getComponentDescriptor().getFields().containsKey(tPriority.field())){
					Node node = getComponentDescriptor().getFields().get(tPriority.field());
					VBox.setVgrow(node, tPriority.priority());
				}else{
					System.out.println("Warning: TVGrowParser cant find the fieldBox for the field "+tPriority.field()+" at fieldBox list, maybe they was not builded yet!");
				}
					
			}
			
		}*/
		
		
	}
	
}
