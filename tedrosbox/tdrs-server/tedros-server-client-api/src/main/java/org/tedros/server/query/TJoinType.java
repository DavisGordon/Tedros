/**
 * 
 */
package org.tedros.server.query;

/**
 * @author Davis Gordon
 *
 */
public enum TJoinType {

	INNER ("join"), LEFT ("left join");
	
	private String value;

	private TJoinType(String value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
}
