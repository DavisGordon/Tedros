package org.tedros.server.result;

import java.io.Serializable;

/**
 * A Service state.
 * 
 *  @author Davis Gordon
 * 
 * */

public class TResult<E> implements Serializable {

	private static final long serialVersionUID = -7635310661063307949L;
	
	/**
	 * SUCCESS(1),
	 * ERROR(-1),
	 * OUTDATED(-2),
	 * WARNING(0);
	 * */
	public enum TState {
		SUCCESS(1),
		ERROR(-1),
		OUTDATED(-2),
		WARNING(0);
		
		private int value;
		
		private TState(int value) {
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
	}
	
	private boolean priorityMessage = false;
	private TState state;
	private String message;
	private E value;
	
	public TResult() {
		
	}
	
	public TResult(TState state) {
		this.state = state;
	}
	
	public TResult(TState state, String message) {
		this.state = state;
		this.message = message;
	}
	
	public TResult(TState state, boolean priorityMessage, String message) {
		this.state = state;
		this.message = message;
		this.priorityMessage = priorityMessage;
	}
	
	public TResult(TState state, String message, E value) {
		this.state = state;
		this.message = message;
		this.value = value;
	}
	
	public TResult(TState state, boolean priorityMessage, String message, E value) {
		this.state = state;
		this.message = message;
		this.value = value;
		this.priorityMessage = priorityMessage;
	}
	
	public TResult(TState state, E value) {
		this.state = state;
		this.value = value;
	}
	
	public TState getState() {
		return state;
	}
	public void setState(TState state) {
		this.state = state;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public E getValue() {
		return value;
	}
	public void setValue(E value) {
		this.value = value;
	}

	/**
	 * @return the priorityMessage
	 */
	public boolean isPriorityMessage() {
		return priorityMessage;
	}

	/**
	 * @param priorityMessage the priorityMessage to set
	 */
	public void setPriorityMessage(boolean priorityMessage) {
		this.priorityMessage = priorityMessage;
	}
	
	
	
}
