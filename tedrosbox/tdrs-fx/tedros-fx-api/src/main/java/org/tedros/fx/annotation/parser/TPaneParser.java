package org.tedros.fx.annotation.parser;

import org.apache.commons.lang3.StringUtils;
import org.tedros.api.descriptor.ITFieldDescriptor;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.exception.TException;

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
		ITFieldDescriptor fd = getComponentDescriptor().getFieldDescriptor();
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
