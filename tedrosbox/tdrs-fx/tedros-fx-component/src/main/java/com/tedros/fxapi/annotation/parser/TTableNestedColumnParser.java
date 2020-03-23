package com.tedros.fxapi.annotation.parser;

import javafx.scene.control.TableColumn;

import com.tedros.fxapi.annotation.control.TTableNestedColumn;

@SuppressWarnings("rawtypes")
public class TTableNestedColumnParser extends TAnnotationParser<TTableNestedColumn, TableColumn> {

	private static TTableNestedColumnParser instance;
	
	private TTableNestedColumnParser(){
		
	}
	
	public static  TTableNestedColumnParser getInstance(){
		if(instance==null)
			instance = new TTableNestedColumnParser();
		return instance;	
	}
	
	@Override
	public void parse(TTableNestedColumn annotation, TableColumn object, String... byPass) throws Exception {
		super.parse(annotation, object, "cellValue");
	}
}
