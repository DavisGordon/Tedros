package com.tedros.fxapi.annotation.parser;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import com.tedros.fxapi.annotation.layout.TAccordion;
import com.tedros.fxapi.annotation.layout.TTitledPane;
import com.tedros.fxapi.descriptor.TComponentDescriptor;
import com.tedros.fxapi.domain.TLayoutType;
import com.tedros.fxapi.form.TComponentBuilder;
import com.tedros.fxapi.form.TFieldBox;

public class TAccordionParser extends TAnnotationParser<TAccordion, Accordion> {

	private static TAccordionParser instance;
	
	private TAccordionParser(){
		
	}
	
	public static  TAccordionParser getInstance(){
		if(instance==null)
			instance = new TAccordionParser();
		return instance;	
	}
	
	@Override
	public void parse(TAccordion annotation, Accordion object, String... byPass) throws Exception {
		
		final TFieldBox firstItem = (TFieldBox) object.getUserData();
		
		for(TTitledPane tTitledPane : annotation.panes()){
			if(tTitledPane.fields().length>0){
				final Pane layout = tTitledPane.layoutType().getValue().newInstance();
				layout.setId("t-form");
				layout.setPadding(new Insets(8,0,8,0));			
				
				if(tTitledPane.layoutType().equals(TLayoutType.VBOX))
					((VBox)layout).setSpacing(10);
			
				for(String field: tTitledPane.fields()){
					Node node = null;
					if(field.equals(firstItem.gettControlFieldName())){
						node = firstItem;
					}else{
						final TComponentDescriptor descriptor = new TComponentDescriptor(getComponentDescriptor(), field);
						node = TComponentBuilder.getField(descriptor);
					}
					if(node!=null){
						layout.getChildren().add(node);
					}
				}
				
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
}
