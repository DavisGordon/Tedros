package org.tedros.fx.annotation.parser;

import org.tedros.api.descriptor.ITFieldDescriptor;
import org.tedros.fx.annotation.control.TScrollPane;
import org.tedros.util.TLoggerUtil;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;

public class TScrollPaneParser extends TAnnotationParser<TScrollPane, ScrollPane> {

	@Override
	public void parse(TScrollPane annotation, ScrollPane scroll, String... byPass) throws Exception {
		
		scroll.sceneProperty().addListener((obs, o, n)->{
			if(n!=null) {
				scroll.autosize();
				//scroll.setPadding(new Insets(10));
				Node node = getNode(annotation.content());
				scroll.setContent(node);
				scroll.getStyleClass().add("tab-content-area");
				
				try {
					super.parse(annotation, scroll, "content");
				} catch (Exception e) {
					TLoggerUtil.error(getClass(), e.getMessage(), e);
				}
			}
		});
		
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
