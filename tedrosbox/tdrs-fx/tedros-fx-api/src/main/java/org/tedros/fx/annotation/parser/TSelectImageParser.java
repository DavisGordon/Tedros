package org.tedros.fx.annotation.parser;

import org.tedros.fx.control.TSelectImageField;
import org.tedros.fx.control.action.TEventHandler;

public class TSelectImageParser extends TControlFieldParser<org.tedros.fx.annotation.control.TSelectImageField, TSelectImageField>{

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void parse(org.tedros.fx.annotation.control.TSelectImageField ann, TSelectImageField control, String... byPass) throws Exception {
		
		control.settScroll(ann.scroll());
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
		control.settHeight(ann.height());
		if(!"".equals(ann.localFolder()))
			control.settLocalFolder(ann.localFolder());
	}
}