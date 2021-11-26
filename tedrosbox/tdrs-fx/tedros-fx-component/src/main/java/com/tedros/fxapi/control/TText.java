/**
 * 
 */
package com.tedros.fxapi.control;

import javafx.scene.text.Text;

/**
 * @author Davis Gordon
 *
 */
public class TText extends Text {

	public enum TTextStyle{
		CUSTOM ("t-form-text"),
		SMALL ("t-form-text-small"),
		MEDIUM ("t-form-text-medium"),
		LARGE ("t-form-text-large");
		
		private String value;
		
		private TTextStyle(String val) {
			this.value = val;
		}

		/**
		 * @return the value
		 */
		public String getValue() {
			return value;
		}
	}
	
	/**
	 * 
	 */
	public TText() {
		super();
		super.getStyleClass().add("t-form-text");
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 */
	public TText(double arg0, double arg1, String arg2) {
		super(arg0, arg1, arg2);
		super.getStyleClass().add("t-form-text");
	}

	/**
	 * @param arg0
	 */
	public TText(String arg0) {
		super(arg0);
		super.getStyleClass().add("t-form-text");
	}
	
	public void settTextStyle(TTextStyle style) {
		super.getStyleClass().clear();
		super.getStyleClass().add(style.getValue());
	}

}
