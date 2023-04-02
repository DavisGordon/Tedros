package org.tedros.fx.annotation.parser;

import org.apache.commons.lang3.StringUtils;
import org.tedros.api.descriptor.ITFieldDescriptor;
import org.tedros.fx.annotation.control.TContent;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;

import javafx.geometry.Insets;
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
				
				ScrollPane scroll = new ScrollPane() ;
				scroll.autosize();
				scroll.setPadding(new Insets(10));
				scroll.setContent(pane);
				scroll.setFitToWidth(true);
				//scroll.maxHeight(Double.MAX_VALUE);
				//scroll.maxWidth(Double.MAX_VALUE);
				if(!tTab.scroll()) {
			    	scroll.setVbarPolicy(ScrollBarPolicy.NEVER);
			    	scroll.setHbarPolicy(ScrollBarPolicy.NEVER);
			    }else {
			    	scroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
			    	scroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
			    }
				scroll.setId("t-tab-content");
				//scroll.layout();
				
				tab.setContent(scroll);
			}
			
			TAnnotationParser.callParser(tTab, tab, getComponentDescriptor());
			object.getTabs().add(tab);
		}
		
		super.parse(annotation, object, "tabs");
		
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
		b.setSpacing(10);
		return b;
	}

	private HBox buildHBox() {
		HBox b = new HBox();
		b.setAlignment(Pos.CENTER_LEFT);
		b.setFillHeight(true);
		b.setSpacing(10);
		return b;
	}
	
}
