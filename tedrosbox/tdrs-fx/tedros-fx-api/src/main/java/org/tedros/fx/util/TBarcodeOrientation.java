/**
 * 
 */
package org.tedros.fx.util;

/**
 * @author Davis Gordon
 *
 */
public enum TBarcodeOrientation {

	_0(0), 
	_90(90),
	_180(180),
	_270(270);
	
	private int value;
	
	private TBarcodeOrientation(int value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

}
