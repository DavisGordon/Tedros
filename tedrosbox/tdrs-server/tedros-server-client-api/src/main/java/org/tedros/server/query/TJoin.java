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
	/**
	 * Specify a join.
	 * Important, the field parameter must precede its source alias.
	 *
	 * @param type
	 * @param field - the field to join, ie: "e.product"
	 * @param alias - the join alias
	 */
	public TJoin(TJoinType type, String field, String alias) {
		super(alias, field);
		this.type = type;
	}
	/**
	 * @return the type
	 */
	public TJoinType getType() {
		return type;
	}
}
