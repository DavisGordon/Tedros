package org.tedros.fx.control;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;

public class TScrollPane extends ScrollPane {

	public TScrollPane() {
		
	}
	
	public TScrollPane(Node node) {
		setContent(node);
	}
	
}
