package org.tedros.fx.domain;

import java.text.DateFormat;

public enum TTimeStyle {
	
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
	private TTimeStyle(int value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

}
