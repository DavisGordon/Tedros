/**
 * 
 */
package com.tedros.ejb.base.security;

/**
 * @author Davis Gordon
 *
 */
public class TClient {

	private String token;
	
	/**
	 * @param token
	 */
	public TClient(String token) {
		this.token = token;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
}
