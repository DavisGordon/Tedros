package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.control.TDirectoryField;
import org.tedros.fx.control.action.TEventHandler;

public class TDirectoryFieldParser extends TAnnotationParser<TDirectoryField, org.tedros.fx.control.TDirectoryField> {
		
	@Override
	public void parse(TDirectoryField tAnnotation, org.tedros.fx.control.TDirectoryField control, String...byPass) throws Exception {
		
		super.parse(tAnnotation, control, "cleanAction", "selectAction", "control", "textInputControl" );
		
		TControlParser cp = new TControlParser();
		TTextInputControlParse ticp = new TTextInputControlParse();
		cp.setComponentDescriptor(getComponentDescriptor());
		cp.parse(tAnnotation.control(), control.gettFileNameField());
		ticp.setComponentDescriptor(getComponentDescriptor());
		ticp.parse(tAnnotation.textInputControl(), control.gettFileNameField());
		
		try {
			if(tAnnotation.cleanAction() != TEventHandler.class)
				control.settCleanAction(tAnnotation.cleanAction().newInstance());
			if(tAnnotation.selectAction() != TEventHandler.class)
				control.settSelectAction(tAnnotation.selectAction().newInstance());
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
}
