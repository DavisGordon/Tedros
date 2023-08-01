/**
 * 
 */
package org.tedros.fx.control;

import org.tedros.app.component.ITActionComponent;
import org.tedros.fx.control.TText.TTextStyle;

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
		getStyleClass().add(TTextStyle.CUSTOM.getValue());
	}

	/**
	 * @param arg0
	 */
	public THyperlink(String arg0) {
		super(arg0);
		getStyleClass().add(TTextStyle.CUSTOM.getValue());
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public THyperlink(String arg0, Node arg1) {
		super(arg0, arg1);
		getStyleClass().add(TTextStyle.CUSTOM.getValue());
	}

	public void settTextStyle(TTextStyle style) {
		super.getStyleClass().clear();
		super.getStyleClass().add(style.getValue());
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
