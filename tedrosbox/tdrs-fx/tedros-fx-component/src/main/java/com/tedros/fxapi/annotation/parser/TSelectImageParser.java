package com.tedros.fxapi.annotation.parser;

import com.tedros.fxapi.control.TSelectImageField;
import com.tedros.fxapi.control.action.TEventHandler;

public class TSelectImageParser extends TControlFieldParser<com.tedros.fxapi.annotation.control.TSelectImageField, TSelectImageField>{

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void parse(com.tedros.fxapi.annotation.control.TSelectImageField ann, TSelectImageField control, String... byPass) throws Exception {
		
		if(!(ann.imageViewEvents().length==1 && ann.imageViewEvents()[0]==TEventHandler.class)) {
			for(Class<? extends TEventHandler> c : ann.imageViewEvents()) {
				TEventHandler ev = c.newInstance();
				ev.setDescriptor(getComponentDescriptor());
				control.addTEventHandler(ev);
			}
		}
		if(ann.fitWidth()>0)
			control.settFitWidth(ann.fitWidth());
		if(ann.fitHeight()>0)
			control.settFitHeight(ann.fitHeight());
		control.settPreLoadBytes(ann.preLoadFileBytes());
		control.settMaxFileSize(ann.maxFileSize());	
	}
}