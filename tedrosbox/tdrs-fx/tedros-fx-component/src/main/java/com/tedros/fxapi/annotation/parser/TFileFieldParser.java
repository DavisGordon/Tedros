package com.tedros.fxapi.annotation.parser;

import org.apache.commons.lang3.ArrayUtils;

import com.tedros.fxapi.annotation.control.TFileField;
import com.tedros.fxapi.control.action.TActionEvent;
import com.tedros.fxapi.domain.TFileExtension;

public class TFileFieldParser extends TAnnotationParser<TFileField, com.tedros.fxapi.control.TFileField> {
	
	@Override
	public void parse(TFileField tAnnotation, com.tedros.fxapi.control.TFileField control, String...byPass) throws Exception {
		
		super.parse(tAnnotation, control, "openAction","loadAction", "imageClickAction", "cleanAction", "selectAction", "control", "textInputControl", "extensions", "moreExtensions");
		
		TControlParser cp = new TControlParser();
		TTextInputControlParse ticp = new TTextInputControlParse();
		cp.setComponentDescriptor(getComponentDescriptor());
		cp.parse(tAnnotation.control(), control.getFileNameField());
		ticp.setComponentDescriptor(getComponentDescriptor());
		ticp.parse(tAnnotation.textInputControl(), control.getFileNameField());
		
		String[] extensions = new String[tAnnotation.extensions().length];
		for(TFileExtension e : tAnnotation.extensions())
			extensions = ArrayUtils.addAll(extensions, e.getExtension());
		
		if(tAnnotation.moreExtensions()!=null && tAnnotation.moreExtensions().length>0)
			extensions = ArrayUtils.addAll(extensions, tAnnotation.moreExtensions());
		control.setExtensions(extensions);
		
		try {
			if(tAnnotation.openAction() != TActionEvent.class)
				control.setOpenAction(tAnnotation.openAction().newInstance());
			if(tAnnotation.cleanAction() != TActionEvent.class)
				control.setCleanAction(tAnnotation.cleanAction().newInstance());
			if(tAnnotation.loadAction() != TActionEvent.class)
				control.setLoadAction(tAnnotation.loadAction().newInstance());
			if(tAnnotation.selectAction() != TActionEvent.class)
				control.setSelectAction(tAnnotation.selectAction().newInstance());
			if(tAnnotation.imageClickAction() != TActionEvent.class)
				control.setImageClickAction(tAnnotation.imageClickAction().newInstance());
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
}
