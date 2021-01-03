package com.tedros.fxapi.annotation.parser;

import com.tedros.fxapi.annotation.control.TDirectoryField;
import com.tedros.fxapi.control.action.TActionEvent;

public class TDirectoryFieldParser extends TAnnotationParser<TDirectoryField, com.tedros.fxapi.control.TDirectoryField> {
		
	@Override
	public void parse(TDirectoryField tAnnotation, com.tedros.fxapi.control.TDirectoryField control, String...byPass) throws Exception {
		
		super.parse(tAnnotation, control, "cleanAction", "selectAction", "control", "textInputControl" );
		
		TControlParser cp = new TControlParser();
		TTextInputControlParse ticp = new TTextInputControlParse();
		cp.setComponentDescriptor(getComponentDescriptor());
		cp.parse(tAnnotation.control(), control.gettFileNameField());
		ticp.setComponentDescriptor(getComponentDescriptor());
		ticp.parse(tAnnotation.textInputControl(), control.gettFileNameField());
		
		try {
			if(tAnnotation.cleanAction() != TActionEvent.class)
				control.settCleanAction(tAnnotation.cleanAction().newInstance());
			if(tAnnotation.selectAction() != TActionEvent.class)
				control.settSelectAction(tAnnotation.selectAction().newInstance());
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
}
