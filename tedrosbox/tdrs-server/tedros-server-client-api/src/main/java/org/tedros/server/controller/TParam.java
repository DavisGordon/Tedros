/**
 * 
 */
package org.tedros.server.controller;

import java.io.Serializable;

/**
 * The generic controller param.
 * @author Davis Gordon
 *
 */
public class TParam<T> implements Serializable {
	
	private static final long serialVersionUID = -7568720223300360958L;

	private String code;
	private T value;
	
	/**
	 * @param value
	 */
	public TParam(T value) {
		this.value = value;
	}
	/**
	 * @param code
	 * @param value
	 */
	public TParam(String code, T value) {
		this.code = code;
		this.value = value;
	}
	/**
	 * Returns the value
	 * @return the value
	 */
	public T getValue() {
		return value;
	}
	/**
	 * Returns the code
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof TParam))
			return false;
		TParam other = (TParam) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
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
		return "TParam [" + (code != null ? "code=" + code + ", " : "") + (value != null ? "value=" + value : "") + "]";
	}

}
