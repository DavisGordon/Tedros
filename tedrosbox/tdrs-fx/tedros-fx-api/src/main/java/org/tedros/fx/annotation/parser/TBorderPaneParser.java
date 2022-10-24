package org.tedros.fx.annotation.parser;

import java.lang.annotation.Annotation;

import org.apache.commons.lang3.ArrayUtils;
import org.tedros.api.descriptor.ITFieldDescriptor;
import org.tedros.fx.annotation.layout.TBorderPane;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class TBorderPaneParser extends TAnnotationParser<Annotation, BorderPane> {

	@Override
	public void parse(Annotation annotation, BorderPane object, String... byPass) throws Exception {
		
		TBorderPane ann = (TBorderPane) annotation;
		
		if(!"".equals(ann.top())) {
			Node node = getNode(ann.top());
			if(node!=null) {
				if(ann.scroll().length>0 && ArrayUtils.contains(ann.scroll(), ann.top()) ) {
					object.setTop(buildScrollPane(node));
				}else
					object.setTop(node);
			}else
				throwException(ann.top());
		}
		if(!"".equals(ann.right())) {
			Node node = getNode(ann.right());
			if(node!=null) {
				if(ann.scroll().length>0 && ArrayUtils.contains(ann.scroll(), ann.right()) ) {
					object.setRight(buildScrollPane(node));
				}else
					object.setRight(node);
			}else
				throwException(ann.right());
		}
		if(!"".equals(ann.bottom())) {
			Node node = getNode(ann.bottom());
			if(node!=null) {
				if(ann.scroll().length>0 && ArrayUtils.contains(ann.scroll(), ann.bottom()) ) {
					object.setBottom(buildScrollPane(node));
				}else
					object.setBottom(node);
			}else
				throwException(ann.bottom());
		}
		if(!"".equals(ann.left())) {
			Node node = getNode(ann.left());
			if(node!=null) {
				if(ann.scroll().length>0 && ArrayUtils.contains(ann.scroll(), ann.left()) ) {
					object.setLeft(buildScrollPane(node));
				}else
					object.setLeft(node);
			}else
				throwException(ann.left());
		}
		if(!"".equals(ann.center())) {
			Node node = getNode(ann.center());
			if(node!=null) {
				if(ann.scroll().length>0 && ArrayUtils.contains(ann.scroll(), ann.center()) ) {
					object.setCenter(buildScrollPane(node));
				}else
					object.setCenter(node);
			}else
				throwException(ann.center());
		}
		super.parse(annotation, object, "top", "right", "bottom", "left", "center", "scroll");
	}


	/**
	 * @param node
	 * @return
	 */
	private StackPane buildScrollPane(Node node) {
		ScrollPane scroll = new ScrollPane();
		scroll.setId("t-form-scroll");
	    scroll.setFitToWidth(true);
	    //scroll.setFitToHeight(true);
	    scroll.maxHeight(Double.MAX_VALUE);
	    scroll.maxWidth(Double.MAX_VALUE);
	    scroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
	    scroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
	    scroll.setStyle("-fx-background-color: transparent;");
		scroll.setContent(node);
		return new StackPane(scroll);
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
