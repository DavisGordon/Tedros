/**
 * 
 */
package org.tedros.web.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Davis Gordon
 *
 */
@XmlRootElement
public class BaseModel<T> implements Serializable {

	private static final long serialVersionUID = 7924379493407255213L;
	private T id;
	private String type;
	
	public BaseModel() {
	}

	public BaseModel(T id) {
		super();
		this.id = id;
	}

	public BaseModel(T id, String type) {
		super();
		this.id = id;
		this.type = type;
	}

	/**
	 * @return the id
	 */
	public T getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(T id) {
		this.id = id;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
}
