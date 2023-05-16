/**
 * 
 */
package org.tedros.server.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.tedros.server.entity.ITEntity;

/**
 * @author Davis Gordon
 *
 */
public class TSelect<E extends ITEntity>  implements Serializable{

	private static final long serialVersionUID = 7796197401200907016L;
	
	public static final String ALIAS = "t0";
	
	private Class<E> type;
	private String alias = ALIAS;
	private List<TJoin> joins;
	private List<TBlock> conditions;
	private List<TField> ordenations;
	/**
	 * @param type
	 */
	public TSelect(Class<E> type) {
		this.type = type;
	}
	
	/**
	 * @param type
	 */
	public TSelect(Class<E> type, String alias) {
		this.type = type;
		this.alias = alias;
	}
	/**
	 * add a join.
	 * Important, the field parameter must precede its source alias.
	 *
	 * @param type
	 * @param field - the field to join, ie: "e.product"
	 * @param alias - the join alias
	 */
	public void addJoin(TJoinType type, String field, String alias) {
		if(joins==null)
			joins = new ArrayList<>();
		joins.add(new TJoin(type, field, alias));
	}

	public <T> void addOrCondition(String field, TCompareOp compOp, T value) {
		this.addOrCondition(ALIAS, field, compOp, value);
	}
	
	public <T> void addOrCondition(String alias, String field, TCompareOp compOp, T value) {
		this.addCondition(TLogicOp.OR, alias, field, compOp, value);
	}
	
	public <T> void addAndCondition(String field, TCompareOp compOp, T value) {
		this.addAndCondition(ALIAS, field, compOp, value);
	}

	public <T> void addAndCondition(String alias, String field, TCompareOp compOp, T value) {
		this.addCondition(TLogicOp.AND, alias, field, compOp, value);
	}
	public <T> void addCondition(TLogicOp logicOp, String alias, String field, TCompareOp compOp, T value) {
		if(this.conditions==null)
			this.conditions = new ArrayList<>();
		TCondition<T> c = new TCondition<T>(alias, field, compOp, value);
		TBlock b = logicOp==null || this.conditions.size()==0
				? new TBlock(c)
						: new TBlock(logicOp, c);
		this.conditions.add(b);
	}

	public <T> void addOrderBy(String field) {
		this.addOrderBy(ALIAS, field);
	}
	
	public <T> void addOrderBy(String alias, String field) {
		if(this.ordenations==null)
			this.ordenations = new ArrayList<>();
		TField f = new TField(alias, field);
		this.ordenations.add(f);
	}

	/**
	 * @return the type
	 */
	public Class<E> getType() {
		return type;
	}

	/**
	 * @return the conditions
	 */
	public List<TBlock> getConditions() {
		return conditions;
	}

	/**
	 * @return the ordenations
	 */
	public List<TField> getOrdenations() {
		return ordenations;
	}

	/**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @return the joins
	 */
	public List<TJoin> getJoins() {
		return joins;
	}
	
}
