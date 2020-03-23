package com.tedros.fxapi.annotation.parser;

import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.ScrollPaneBuilder;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBoxBuilder;

import org.apache.commons.lang3.StringUtils;

import com.tedros.core.ITModule;
import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.fxapi.annotation.control.TContent;
import com.tedros.fxapi.annotation.control.TTab;
import com.tedros.fxapi.annotation.control.TTabPane;
import com.tedros.fxapi.annotation.form.TDetailView;
import com.tedros.fxapi.descriptor.TComponentDescriptor;
import com.tedros.fxapi.form.TComponentBuilder;
import com.tedros.fxapi.presenter.view.TView;

public class TTabPaneParser extends TAnnotationParser<TTabPane, TabPane> {

	private static TTabPaneParser instance;
	
	private TTabPaneParser() {
		
	}
	
	public static TTabPaneParser getInstance() {
		if(instance==null)
			instance = new TTabPaneParser();
		return instance;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
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
						? HBoxBuilder.create()
							.alignment(Pos.CENTER_LEFT)
							.fillHeight(true)
							.build()
								: VBoxBuilder.create()
									.alignment(Pos.CENTER_LEFT)
									.fillWidth(true)
									.build();
						
				for (String field : fields) {
					if(StringUtils.isBlank(field))
						continue;
					
					Node node = null;
					
					if(getComponentDescriptor().getFieldBoxMap().containsKey(field)){
						node = getComponentDescriptor().getFieldBoxMap().get(field);
					}else{
						final TComponentDescriptor descriptor = new TComponentDescriptor(getComponentDescriptor(), field);
						node = TComponentBuilder.getField(descriptor);
					}
					
					if(node!=null)
						pane.getChildren().add(node);
				}
				
				ScrollPane scroll = ScrollPaneBuilder.create()
						.content(pane)
						.fitToWidth(true)
						.maxHeight(Double.MAX_VALUE)
						.maxWidth(Double.MAX_VALUE)
						.vbarPolicy(ScrollBarPolicy.AS_NEEDED)
						.hbarPolicy(ScrollBarPolicy.AS_NEEDED)
						.id("t-form-scroll")
						.build();
				scroll.layout();
				
				tab.setContent(scroll);
				
				
			}else if(!(tContent.detailView().entityClass() == ITEntity.class)){
				TDetailView tDetailView = tContent.detailView();
				Class detailViewClass = tDetailView.viewClass();
				
				try {
					final TComponentDescriptor descriptor = new TComponentDescriptor(getComponentDescriptor(), tDetailView.field());
					
					ITModule module = getComponentDescriptor().getForm().gettPresenter().getModule();
					
					if(module==null){
						final TView view = (TView) detailViewClass.getConstructor(Class.class, Class.class, ObservableList.class)
								.newInstance(tDetailView.entityModelViewClass(), tDetailView.entityClass(), descriptor.getFieldValue());
						
						view.gettPresenter().loadView();					
						tab.setContent(view);
					}else{
						final TView view = (TView) detailViewClass.getConstructor(ITModule.class, Class.class, Class.class, ObservableList.class)
								.newInstance(module, tDetailView.entityModelViewClass(), tDetailView.entityClass(), descriptor.getFieldValue());
						
						view.gettPresenter().loadView();					
						tab.setContent(view);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
			TAnnotationParser.callParser(tTab, tab, getComponentDescriptor());
			object.getTabs().add(tab);
		}
		
		super.parse(annotation, object, "tabs");
		
	}
	
}
