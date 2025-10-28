package org.tedros.core.control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

public class MultipleKeyCombination extends KeyCombination {
    
	private List<KeyCode> neededCodes;
	
	private static List<KeyCode> codes = new ArrayList<KeyCode>();
	
	public static void setupMultipleKeyCombination(Node node) {
		node.addEventFilter(KeyEvent.KEY_PRESSED, (event) -> {
			if (!codes.contains(event.getCode())) {
				codes.add(event.getCode());
			}
		});
		node.setOnKeyReleased((event) -> {
			codes.remove(event.getCode());
		});
	}
	
	public MultipleKeyCombination (KeyCode... codes) {
		neededCodes = Arrays.asList(codes);
	}
	
	
	@Override
	public boolean match(KeyEvent event) {
		return codes.containsAll(neededCodes);
	}
}
