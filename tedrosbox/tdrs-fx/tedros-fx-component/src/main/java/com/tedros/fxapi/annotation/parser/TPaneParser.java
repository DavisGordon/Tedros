package com.tedros.fxapi.annotation.parser;

import org.apache.commons.lang3.StringUtils;

import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.descriptor.TFieldDescriptor;
import com.tedros.fxapi.exception.TException;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class TPaneParser extends TAnnotationParser<TPane, Pane> {
	

	@Override
	public void parse(TPane annotation, final Pane object, String... byPass) throws Exception {

		for(String field : annotation.children()){
			if(StringUtils.isBlank(field))
				continue;
			addNode(object, field);
		}
	}

	private void addNode(final Pane object, String field) {
		TFieldDescriptor fd = getComponentDescriptor().getFieldDescriptor();
		if(fd.hasParent())
			return;
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
		
		if(node!=null)
			object.getChildren().add(node);
		else
			throw new RuntimeException("The field "+field+" hasn't configured with a componet to be used."
					+ getComponentDescriptor().getModelView().getClass().getSimpleName());
	}
	
}
