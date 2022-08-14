package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.layout.TAccordion;
import org.tedros.fx.annotation.layout.TTitledPane;
import org.tedros.fx.descriptor.TFieldDescriptor;
import org.tedros.fx.domain.TLayoutType;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class TAccordionParser extends TAnnotationParser<TAccordion, Accordion> {

	@Override
	public void parse(TAccordion annotation, Accordion object, String... byPass) throws Exception {
		for(TTitledPane tTitledPane : annotation.panes()){
			if(tTitledPane.fields().length>0){
				final Pane layout = tTitledPane.layoutType().getValue().newInstance();
				layout.setId("t-form");
				layout.setPadding(new Insets(8,0,8,0));			
				
				if(tTitledPane.layoutType().equals(TLayoutType.VBOX))
					((VBox)layout).setSpacing(10);
				
				for(final String field: tTitledPane.fields())
					addNode(layout, field);
				
				TitledPane titledPane = new TitledPane();
				titledPane.setContent(layout);
				callParser(tTitledPane, titledPane, getComponentDescriptor());
				object.getPanes().add(titledPane);
				if(titledPane.getId()!=null && annotation.expandedPane().equals(titledPane.getId()))
					object.setExpandedPane(titledPane);
			
			}
		}
		super.parse(annotation, object, "panes");
		
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
			fd.setHasParent(true);
			 node = fd.hasLayout() 
					 ? fd.getLayout()
							 : fd.getComponent();
		}
		if(node!=null && !object.getChildren().contains(node))
			object.getChildren().add(node);
	}
}
