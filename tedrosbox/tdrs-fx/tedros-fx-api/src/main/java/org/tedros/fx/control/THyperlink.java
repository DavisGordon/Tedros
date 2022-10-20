/**
 * 
 */
package org.tedros.fx.control;

import org.tedros.app.component.ITActionComponent;

import javafx.scene.Node;
import javafx.scene.control.Hyperlink;

/**
 * @author Davis Gordon
 *
 */
public class THyperlink extends Hyperlink implements ITActionComponent {

	private String tComponentId;
	/**
	 * 
	 */
	public THyperlink() {
	}

	/**
	 * @param arg0
	 */
	public THyperlink(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public THyperlink(String arg0, Node arg1) {
		super(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see org.tedros.app.component.ITComponent#gettComponentId()
	 */
	@Override
	public String gettComponentId() {
		return tComponentId;
	}

	/* (non-Javadoc)
	 * @see org.tedros.app.component.ITComponent#settComponentId(java.lang.String)
	 */
	@Override
	public void settComponentId(String id) {
		this.tComponentId = id;
	}

}
