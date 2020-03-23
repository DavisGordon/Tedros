package com.tedros.fxapi.annotation.parser;

import javafx.scene.control.TableColumn;

import com.tedros.fxapi.annotation.control.TTableSubColumn;

@SuppressWarnings("rawtypes")
public class TTableSubColumnParser extends TAnnotationParser<TTableSubColumn, TableColumn> {

	private static TTableSubColumnParser instance;
	
	private TTableSubColumnParser(){
		
	}
	
	public static  TTableSubColumnParser getInstance(){
		if(instance==null)
			instance = new TTableSubColumnParser();
		return instance;	
	}
	
	@Override
	public void parse(TTableSubColumn annotation, TableColumn object, String... byPass) throws Exception {
		super.parse(annotation, object, "cellValue", "columns");
	}
}
