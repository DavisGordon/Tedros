/**
 * 
 */
package org.tedros.fx.util;

/**
 * @author Davis Gordon
 *
 */
public enum TBarcodeResolution {

	_100(100), 
	_200(200),
	_300(300),
	_400(400),
	_500(500),
	_600(600);
	
	private int value;

	private TBarcodeResolution(int value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}
}
