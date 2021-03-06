package com.tedros.fxapi.annotation.parser;

import org.apache.commons.lang3.StringUtils;

import com.tedros.fxapi.annotation.control.TContent;
import com.tedros.fxapi.annotation.control.TTab;
import com.tedros.fxapi.annotation.control.TTabPane;
import com.tedros.fxapi.descriptor.TFieldDescriptor;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class TTabPaneParser extends TAnnotationParser<TTabPane, TabPane> {

	@Override
	public void parse(TTabPane annotation, final TabPane object, String... byPass) throws Exception {
		
		TTab[] tabs = annotation.tabs();		
		for (TTab tTab : tabs) {
			
			TContent tContent = tTab.content();
			Tab tab = new Tab();
			
			if(!(tContent.detailForm().fields()[0].equals("") && tContent.detailForm().fields().length==1)){
				String[] fields =  tContent.detailForm().fields();
				Orientation orientation = tContent.detailForm().orientation();
				Pane pane = (orientation.equals(Orientation.HORIZONTAL)) 
						? buildHBox()
								: buildVBox();
						
				for (String field : fields) {
					if(StringUtils.isBlank(field))
						continue;
					addNode(pane, field);
				}
				
				ScrollPane scroll = new ScrollPane();
				scroll.setContent(pane);
				scroll.setFitToWidth(true);
				scroll.maxHeight(Double.MAX_VALUE);
				scroll.maxWidth(Double.MAX_VALUE);
				scroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
				scroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
				scroll.setId("t-form-scroll");
				scroll.layout();
				
				tab.setContent(scroll);
			}
			
			TAnnotationParser.callParser(tTab, tab, getComponentDescriptor());
			object.getTabs().add(tab);
		}
		
		super.parse(annotation, object, "tabs");
		
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
	

	private VBox buildVBox() {
		VBox b = new VBox();
		b.setAlignment(Pos.CENTER_LEFT);
		b.setFillWidth(true);
		return b;
	}

	private HBox buildHBox() {
		HBox b = new HBox();
		b.setAlignment(Pos.CENTER_LEFT);
		b.setFillHeight(true);
		return b;
	}
	
}
