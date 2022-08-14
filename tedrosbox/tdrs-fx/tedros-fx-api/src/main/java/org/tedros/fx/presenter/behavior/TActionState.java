/**
 * 
 */
package org.tedros.fx.presenter.behavior;

import org.apache.commons.lang3.StringUtils;

import javafx.concurrent.Worker.State;

/**
 * @author Davis Gordon
 *
 */
public final class TActionState<T> {

	private TActionType type;
	private State state;
	private TProcessResult result;
	private String message;
	private T value;
	
	/**
	 * @param type
	 * @param state
	 * @param result
	 */
	public TActionState(TActionType type) {
		this.type = type;
		this.state = null;
		this.result = null;
		this.message = null;
		this.value = null;
	}
	
	/**
	 * @param type
	 * @param state
	 * @param result
	 */
	public TActionState(TActionType type, State state) {
		this.type = type;
		this.state = state;
		this.result = null;
		this.message = null;
		this.value = null;
	}

	/**
	 * @param type
	 * @param state
	 * @param result
	 */
	public TActionState(TActionType type, TProcessResult result) {
		this.type = type;
		this.state = null;
		this.result = result;
		this.message = null;
		this.value = null;
	}
	
	/**
	 * @param type
	 * @param state
	 * @param result
	 */
	public TActionState(TActionType type, State state, TProcessResult result) {
		this.type = type;
		this.state = state;
		this.result = result;
		this.message = null;
		this.value = null;
	}

	/**
	 * @param type
	 * @param result
	 * @param message
	 */
	public TActionState(TActionType type, TProcessResult result, String message) {
		this.type = type;
		this.state = null;
		this.result = result;
		this.message = message;
		this.value = null;
	}
	
	/**
	 * @param type
	 * @param state
	 * @param result
	 * @param message
	 */
	public TActionState(TActionType type, State state, TProcessResult result, String message) {
		this.type = type;
		this.state = state;
		this.result = result;
		this.message = message;
		this.value = null;
	}
	
	/**
	 * @param type
	 * @param state
	 * @param result
	 * @param value
	 */
	public TActionState(TActionType type, State state, TProcessResult result, T value) {
		this.type = type;
		this.state = state;
		this.result = result;
		this.message = null;
		this.value = value;
	}

	/**
	 * @param type
	 * @param state
	 * @param result
	 * @param message
	 * @param value
	 */
	public TActionState(TActionType type, State state, TProcessResult result, String message, T value) {
		this.type = type;
		this.state = state;
		this.result = result;
		this.message = message;
		this.value = value;
	}

	public TActionState(TActionType type, TProcessResult result, T value) {
		this.type = type;
		this.state = null;
		this.result = result;
		this.message = null;
		this.value = value;
	}

	public boolean hasMessage() {
		return StringUtils.isNotBlank(message);
	}

	/**
	 * @return the type
	 */
	public TActionType getType() {
		return type;
	}

	/**
	 * @return the state
	 */
	public State getState() {
		return state;
	}

	/**
	 * @return the result
	 */
	public TProcessResult getResult() {
		return result;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the value
	 */
	public T getValue() {
		return value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings({"rawtypes" })
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TActionState other = (TActionState) obj;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (result != other.result)
			return false;
		if (state != other.state)
			return false;
		if (type != other.type)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TActionState [" + (type != null ? "type=" + type + ", " : "")
				+ (state != null ? "state=" + state + ", " : "") + (result != null ? "result=" + result + ", " : "")
				+ (message != null ? "message=" + message + ", " : "") + (value != null ? "value=" + value : "") + "]";
	}

}
