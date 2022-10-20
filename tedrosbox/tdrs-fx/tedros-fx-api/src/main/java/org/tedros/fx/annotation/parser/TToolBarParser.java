package org.tedros.fx.annotation.parser;

import java.lang.annotation.Annotation;

import org.tedros.api.descriptor.ITFieldDescriptor;
import org.tedros.fx.annotation.layout.TToolBar;

import javafx.scene.Node;

public class TToolBarParser extends TAnnotationParser<Annotation, org.tedros.fx.layout.TToolBar> {

	@Override
	public void parse(Annotation annotation, org.tedros.fx.layout.TToolBar object, String... byPass) throws Exception {
		
		TToolBar ann = (TToolBar) annotation;
		
		if(ann.items().length>0) {
			for(String field : ann.items()) {
				Node node = getNode(field);
				if(node!=null)
					object.getItems().add(node);
				else
					throwException(field);
			}
		}
		
		super.parse(annotation, object, "items");
	}


	/**
	 * @param ann
	 */
	private void throwException(String field) {
		throw new RuntimeException("The field "+field+" hasn't configured with a componet to be used."
				+ getComponentDescriptor().getModelView().getClass().getSimpleName());
	}
	

	private Node getNode(String field) {
		ITFieldDescriptor fd = getComponentDescriptor().getFieldDescriptor();
		if(fd.hasParent())
			return null;
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
		return node;
	}
	
}
