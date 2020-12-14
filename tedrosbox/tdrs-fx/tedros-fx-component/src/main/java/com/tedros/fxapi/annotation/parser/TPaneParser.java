package com.tedros.fxapi.annotation.parser;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

import org.apache.commons.lang3.StringUtils;

import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.descriptor.TComponentDescriptor;
import com.tedros.fxapi.form.TComponentBuilder;
import com.tedros.fxapi.form.TFieldBox;

public class TPaneParser extends TAnnotationParser<TPane, Pane> {

	private static TPaneParser instance;
	
	private TPaneParser() {
		
	}
	
	public static TPaneParser getInstance() {
		if(instance==null)
			instance = new TPaneParser();
		return instance;
	}

	@Override
	public void parse(TPane annotation, final Pane object, String... byPass) throws Exception {
		
		TFieldBox fb = (TFieldBox) object.getUserData();
		for(String field : annotation.children()){
			if(StringUtils.isBlank(field))
				continue;
			
			Node node = null;
			
			if( fb.gettControlFieldName().equals(field) ) {
				node = fb;
			}else {
			
				if(getComponentDescriptor().getFieldBoxMap().containsKey(field)){
					node = getComponentDescriptor().getFieldBoxMap().get(field);
				}else{
					final TComponentDescriptor descriptor = new TComponentDescriptor(getComponentDescriptor(), field);
					node = TComponentBuilder.getField(descriptor);
				}
			}
			if(node!=null)
				object.getChildren().add(node);
		}
	}
	
}
