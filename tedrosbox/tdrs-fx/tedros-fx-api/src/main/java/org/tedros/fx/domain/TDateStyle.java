package org.tedros.fx.domain;

import java.text.DateFormat;

public enum TDateStyle {
	
	NONE (-1),
	DEFAULT (DateFormat.DEFAULT),
	SHORT (DateFormat.SHORT),
	MEDIUM (DateFormat.MEDIUM),
	LONG (DateFormat.LONG),
	FULL (DateFormat.FULL);
	
	private int value;

	/**
	 * @param value
	 */
	private TDateStyle(int value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

}
