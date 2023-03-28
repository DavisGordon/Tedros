package org.tedros.server.query;

import java.io.Serializable;

public class TField  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4277238013497442351L;
	private String field;
	/**
	 * @param field
	 */
	public TField(String field) {
		this.field = field;
	}
	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}
}