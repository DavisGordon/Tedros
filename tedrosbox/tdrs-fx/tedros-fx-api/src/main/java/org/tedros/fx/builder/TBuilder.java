package org.tedros.fx.builder;

import java.lang.annotation.Annotation;

import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.api.descriptor.ITFieldDescriptor;
import org.tedros.core.ITModule;
import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.fx.annotation.parser.TAnnotationParser;
import org.tedros.util.TLoggerUtil;

import javafx.scene.Node;


public abstract class TBuilder implements ITBuilder {
	
	private ITComponentDescriptor componentDescriptor;
	
	protected TLanguage iEngine = TLanguage.getInstance(null);
	
	public ITComponentDescriptor getComponentDescriptor() {
		return componentDescriptor;
	}
	
	public void setComponentDescriptor(ITComponentDescriptor annotationDescriptor) {
		this.componentDescriptor = annotationDescriptor;
		
		try{
			if(this.componentDescriptor.getForm()!=null && this.componentDescriptor.getForm().gettPresenter()!=null){
				ITModule module = this.componentDescriptor.getForm().gettPresenter().getModule();
				if(module !=null && TedrosAppManager.getInstance().getModuleContext(module)!=null){
					String uuid = TedrosAppManager.getInstance().getModuleContext(module).getAppContext().getAppDescriptor().getUniversalUniqueIdentifier();
					iEngine.setCurrentUUID(uuid);
				}
			}
		}catch(Exception e){
			TLoggerUtil.error(getClass(),e.getMessage(), e);
		}
		
	}
	
	public void callParser(final Annotation tAnnotation, final Object control) throws Exception {
		TAnnotationParser.callParser(tAnnotation, control, componentDescriptor);
	}
	
	protected Node getNode(String field) {
		ITFieldDescriptor fd = getComponentDescriptor().getFieldDescriptor();
		
		Node node = null;
		if(fd.getFieldName().equals(field)) {
			node = fd.getComponent();
		}else {
			fd = getComponentDescriptor().getFieldDescriptor(field);
			if(fd==null)
				throw new RuntimeException("The field "+field+" was not found in "
			+ getComponentDescriptor().getModelView().getClass().getSimpleName());
			
			fd.setHasParent(true);
			node = fd.hasLayout() 
					 ? fd.getLayout()
							 : fd.getComponent();
		}
		
		if(node==null)
			throw new RuntimeException("The field "+field+" hasn't configured with a componet to be used."
					+ getComponentDescriptor().getModelView().getClass().getSimpleName());
		return node;
	}
	
}
