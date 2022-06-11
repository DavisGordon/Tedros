/**
 * 
 */
package com.tedros.core.notify.model;

/**
 * @author Davis Gordon
 *
 */
public enum TAction {
	
	NONE ("#{label.none}"),
	TO_QUEUE("#{label.to.queue}"),
	TO_SCHEDULE("#{label.to.schedule}"),
	SEND("#{label.send}"),
	CANCEL("#{label.cancel}");
	
	private String value;

	/**
	 * @param value
	 */
	private TAction(String value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

}
