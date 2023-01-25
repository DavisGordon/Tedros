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
public class NamedModel<T> extends ValueModel<T> {

	private static final long serialVersionUID = -7341817410463831389L;
	private String name;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	public NamedModel(T id, String value, String name) {
		super(id, value);
		this.name = name;
	}

	public NamedModel(T id, String type, String value, String name) {
		super(id, type, value);
		this.name = name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


}
