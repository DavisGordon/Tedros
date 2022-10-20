package org.tedros.fx.annotation.parser;

import java.lang.annotation.Annotation;

import org.tedros.api.descriptor.ITFieldDescriptor;
import org.tedros.fx.annotation.layout.TBorderPane;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class TBorderPaneParser extends TAnnotationParser<Annotation, BorderPane> {

	@Override
	public void parse(Annotation annotation, BorderPane object, String... byPass) throws Exception {
		
		TBorderPane ann = (TBorderPane) annotation;
		
		if(!"".equals(ann.top())) {
			Node node = getNode(ann.top());
			if(node!=null)
				object.setTop(node);
			else
				throwException(ann.top());
		}
		if(!"".equals(ann.right())) {
			Node node = getNode(ann.right());
			if(node!=null)
				object.setRight(node);
			else
				throwException(ann.right());
		}
		if(!"".equals(ann.bottom())) {
			Node node = getNode(ann.bottom());
			if(node!=null)
				object.setBottom(node);
			else
				throwException(ann.bottom());
		}
		if(!"".equals(ann.left())) {
			Node node = getNode(ann.left());
			if(node!=null)
				object.setLeft(node);
			else
				throwException(ann.left());
		}
		if(!"".equals(ann.center())) {
			Node node = getNode(ann.center());
			if(node!=null)
				object.setCenter(node);
			else
				throwException(ann.center());
		}
		super.parse(annotation, object, "top", "right", "bottom", "left", "center");
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
