package org.tedros.web.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="credential")
public class CredentialModel implements Serializable{

	private static final long serialVersionUID = 8197993849855849422L;

	@XmlAttribute
	private String email;
	
	@XmlAttribute
	private String pass;
	
	@XmlAttribute
	private String utype;
	
	@XmlAttribute
	private String key;

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the pass
	 */
	public String getPass() {
		return pass;
	}

	/**
	 * @param pass the pass to set
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}

	/**
	 * @return the utype
	 */
	public String getUtype() {
		return utype;
	}

	/**
	 * @param utype the utype to set
	 */
	public void setUtype(String utype) {
		this.utype = utype;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
	
	
	
}
