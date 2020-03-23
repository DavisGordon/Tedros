package com.tedros.fxapi.annotation.parser;

import com.tedros.fxapi.annotation.layout.TAlignment;

public class TAlignmentParser extends TAnnotationParser<TAlignment, Object> {

	private static TAlignmentParser instance;
	
	private TAlignmentParser() {
		
	}
	
	public static TAlignmentParser getInstance() {
		if(instance==null)
			instance = new TAlignmentParser();
		return instance;
	}

	@Override
	public void parse(TAlignment annotation, Object object, String... byPass) throws Exception {
		
		
	}
	
}
