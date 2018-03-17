package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;

import com.tedros.core.ITModule;
import com.tedros.core.TInternationalizationEngine;
import com.tedros.core.context.TedrosAppManager;
import com.tedros.fxapi.annotation.parser.TAnnotationParser;
import com.tedros.fxapi.descriptor.TComponentDescriptor;


public abstract class TBuilder implements ITBuilder {
	
	private TComponentDescriptor componentDescriptor;
	
	protected TInternationalizationEngine iEngine = TInternationalizationEngine.getInstance(null);
	
	public TComponentDescriptor getComponentDescriptor() {
		return componentDescriptor;
	}
	
	public void setComponentDescriptor(TComponentDescriptor annotationDescriptor) {
		this.componentDescriptor = annotationDescriptor;
		
		try{
			if(this.componentDescriptor.getForm().gettPresenter()!=null){
				ITModule module = this.componentDescriptor.getForm().gettPresenter().getModule();
				if(module !=null && TedrosAppManager.getInstance().getModuleContext(module)!=null){
					String uuid = TedrosAppManager.getInstance().getModuleContext(module).getAppContext().getAppDescriptor().getUniversalUniqueIdentifier();
					iEngine.setCurrentUUID(uuid);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void callParser(final Annotation tAnnotation, final Object control) throws Exception {
		TAnnotationParser.callParser(tAnnotation, control, componentDescriptor);
	}
	
}
