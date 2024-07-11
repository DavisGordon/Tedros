package org.tedros.server.query;

import java.io.Serializable;

public class TField  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4277238013497442351L;
	private String alias;
	private String field;
	private String qryParam;
	
	public TField(String alias, String field) {
		super();
		this.alias = alias;
		this.field = field;
	}
	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}
	/**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}
	
	public String getQryParam() {
		return qryParam;
	}
	
	public void setQryParam(String qryParam) {
		this.qryParam = qryParam;
	}
}