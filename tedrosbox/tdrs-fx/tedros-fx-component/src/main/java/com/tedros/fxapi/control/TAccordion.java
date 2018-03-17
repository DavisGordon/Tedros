/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 09/11/2013
 */
package com.tedros.fxapi.control;

import java.util.HashMap;
import java.util.Map;

import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;

import com.sun.javafx.collections.ObservableMapWrapper;
import com.tedros.app.component.ITComponent;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TAccordion extends Accordion implements ITComponent {

	private ObservableMap<String, Node> itens;
	private String t_componentId;
	
	public TAccordion() {
		carregar(null);
	}
	
	public TAccordion(Map<String, Node> itensMap) {
		carregar(itensMap);
	}

	public void carregar(Map<String, Node> itensMap) {
		
		itens = new ObservableMapWrapper<>(new HashMap<String, Node>());
		itens.addListener(new MapChangeListener<String, Node>() {
			@Override
			public void onChanged(javafx.collections.MapChangeListener.Change<? extends String, ? extends Node> mapChanged) {
				
				TitledPane item = new TitledPane();
	        	item.setExpanded(false);
	        	item.setAnimated(true);
	        	item.setCollapsible(true);
	        	item.setText(mapChanged.getKey());
	        	//Text content = TextBuilder.create().text(mapChanged.getKey()).wrappingWidth(350).build();
	        	item.setContent(mapChanged.getValueAdded());
	        	getPanes().add(item);
				
			}
		});
		itens.putAll(itensMap);
	}
	
	public void addItem(String label, Node content){
		itens.put(label, content);
	}
	
	public void addItem(Map<String, Node> map){
		this.itens.putAll(map);
	}

	public ObservableMap<String, Node> getItens() {
		return itens;
	}

	public void setItens(ObservableMap<String, Node> itens) {
		this.itens = itens;
	}
	
	@Override
	public String gettComponentId() {
		return t_componentId;
	}
	
	@Override
	public void settComponentId(String id) {
		t_componentId = id;
	}
	
}
