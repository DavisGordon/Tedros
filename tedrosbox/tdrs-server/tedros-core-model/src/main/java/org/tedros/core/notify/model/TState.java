/**
 * 
 */
package org.tedros.core.notify.model;

/**
 * @author Davis Gordon
 *
 */
public enum TState {
	
	QUEUED ("#{label.queued}"),
	SCHEDULED ("#{label.scheduled}"),
	SENT ("#{label.sent}"),
	CANCELED ("#{label.canceled}"),
	ERROR ("#{label.error}");

	private String value;

	/**
	 * @param value
	 */
	private TState(String value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return value;	
	}
}
