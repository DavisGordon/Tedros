/**
 * 
 */
package org.tedros.fx.layout;

import javafx.scene.Node;
import javafx.scene.control.ToolBar;

/**
 * @author Davis Gordon
 *
 */
public class TToolBar extends ToolBar {

	/**
	 * 
	 */
	public TToolBar() {
		setId("t-view-toolbar");
	}

	/**
	 * @param arg0
	 */
	public TToolBar(Node... arg0) {
		super(arg0);
		setId("t-view-toolbar");
	}

}
