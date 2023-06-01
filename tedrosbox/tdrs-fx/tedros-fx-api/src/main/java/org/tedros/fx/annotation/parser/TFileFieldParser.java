package org.tedros.fx.annotation.parser;

import java.io.File;

import org.apache.commons.lang3.ArrayUtils;
import org.tedros.fx.annotation.control.TFileField;
import org.tedros.fx.control.action.TEventHandler;
import org.tedros.fx.domain.TFileExtension;
import org.tedros.util.TedrosFolder;

public class TFileFieldParser extends TAnnotationParser<TFileField, org.tedros.fx.control.TFileField> {
	
	@Override
	public void parse(TFileField tAnnotation, org.tedros.fx.control.TFileField control, String...byPass) throws Exception {
		
		super.parse(tAnnotation, control, "initialDirectory", "openAction","imageAction", "cleanAction", "selectAction", 
				"control", "textInputControl", "extensions", "moreExtensions", "preLoadFileBytes", "propertyValueType");
		
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
		
		String initFolder = tAnnotation.initialDirectory();
		if(initFolder.contains(TFileField.USER_HOME))
			initFolder = initFolder.replace(TFileField.USER_HOME, System.getProperty("user.home")+File.separator);
		else if(initFolder.contains(TFileField.TEDROS_ROOT))
			initFolder = initFolder.replace(TFileField.TEDROS_ROOT, TedrosFolder.ROOT_FOLDER.getFullPath());
		else if(initFolder.contains(TFileField.TEDROS_MODULE))
			initFolder = initFolder.replace(TFileField.TEDROS_MODULE, TedrosFolder.MODULE_FOLDER.getFullPath());
		
		control.setInitialDirectory(initFolder);
		
		try {
			if(tAnnotation.openAction() != TEventHandler.class)
				control.setOpenAction(tAnnotation.openAction().newInstance());
			if(tAnnotation.cleanAction() != TEventHandler.class)
				control.setCleanAction(tAnnotation.cleanAction().newInstance());
			if(tAnnotation.selectAction() != TEventHandler.class)
				control.setSelectAction(tAnnotation.selectAction().newInstance());
			if(tAnnotation.imageAction() != TEventHandler.class)
				control.setImageAction(tAnnotation.imageAction().newInstance());
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
}
