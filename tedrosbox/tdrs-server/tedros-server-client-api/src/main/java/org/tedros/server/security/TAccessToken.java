/**
 * 
 */
package org.tedros.server.security;

import java.io.Serializable;

/**
 * @author Davis Gordon
 *
 */
public class TAccessToken implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2699002059995469235L;
	private String token;
	
	/**
	 * @param token
	 */
	public TAccessToken(String token) {
		this.token = token;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TAccessToken other = (TAccessToken) obj;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		return true;
	}
}
