/**
 * 
 */
package org.tedros.web.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Davis Gordon
 *
 */
@XmlRootElement
public class ValueModel<T> extends BaseModel<T> {

	private static final long serialVersionUID = 5047237793755122706L;
	
	private String value;
	
	public ValueModel() {
	}

	public ValueModel(T id, String value) {
		super(id);
		this.value = value;
	}

	public ValueModel(T id, String type, String value) {
		super(id, type);
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
