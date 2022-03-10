package com.tedros.fxapi.control;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class TButton extends Button {

	public TButton() {
		setId("t-button");
		addListener();
	}

	/**
	 * 
	 */
	private void addListener() {
		super.parentProperty().addListener((a, o, n)->{
			if(n!=null) {
				ObservableList<Node> l = n.getChildrenUnmodifiable();
				applyStyle(l);
			}
		});
	}

	/**
	 * @param l
	 */
	private void applyStyle(ObservableList<Node> l) {
		if(l!=null && l.size()>0) {
			for(int i=0; i<l.size(); i++) {
				Node n  = l.get(i);
				if(!(n instanceof Button))
					continue;
				if(l.size()>1 && i==l.size()-1) {
					if(l.get(i-1) instanceof Button) {
						n.setId("t-last-button");
					}else
						n.setId("t-button");
				}else if(l.size()>2 && i>0 && i<l.size()-1) {
					if(l.get(i-1) instanceof Button && l.get(i+1) instanceof Button) {
						n.setId("t-middle-button");
					}else if(!(l.get(i-1) instanceof Button) && l.get(i+1) instanceof Button) {
						n.setId("t-first-button");
					}else if(l.get(i-1) instanceof Button && !(l.get(i+1) instanceof Button)) {
						n.setId("t-last-button");
					}else
						n.setId("t-button");
				}else if(l.size()>=1 && i==0) {
					if(l.size()>1 && l.get(i+1) instanceof Button) {
						n.setId("t-first-button");
					}else {
						n.setId("t-button");
					}
				}
			}
		}
	}
	
	public TButton(String text) {
		setText(text);
		setId("t-button");
		addListener();
	}
	
	public TButton(String id, String text) {
		setText(text);
		setId(id);
		getStyleClass().add("t-button");
		
		
	}
	
	
	
	
	
}
