/**
 * 
 */
package org.tedros.server.query;

/**
 * @author Davis Gordon
 *
 */
public class TJoin extends TField{

	private static final long serialVersionUID = 6784731794552244238L;

	private TJoinType type;
	private String joinAlias;
	/**
	 * Specify a join.
	 *
	 * @param type
	 * @param field - the field to join, ie: "e.product"
	 * @param alias - the join alias
	 */
	public TJoin(TJoinType type, String fieldAlias, String field, String joinAlias) {
		super(fieldAlias, field);
		this.type = type;
		this.joinAlias = joinAlias;
	}
	/**
	 * @return the type
	 */
	public TJoinType getType() {
		return type;
	}
	/**
	 * @return the joinAlias
	 */
	public String getJoinAlias() {
		return joinAlias;
	}
}
