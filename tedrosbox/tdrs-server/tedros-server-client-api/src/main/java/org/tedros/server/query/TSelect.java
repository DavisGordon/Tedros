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
	private Class<E> type;
	private List<TBlock> conditions;
	private List<TField> ordenations;
	/**
	 * @param type
	 */
	public TSelect(Class<E> type) {
		this.type = type;
	}

	public <T> void addOrCondition(String field, TCompareOp compOp, T value) {
		this.addCondition(TLogicOp.OR, field, compOp, value);
	}
	
	public <T> void addAndCondition(String field, TCompareOp compOp, T value) {
		this.addCondition(TLogicOp.AND, field, compOp, value);
	}
	
	public <T> void addCondition(TLogicOp logicOp, String field, TCompareOp compOp, T value) {
		if(this.conditions==null)
			this.conditions = new ArrayList<>();
		TCondition<T> c = new TCondition<T>(field, compOp, value);
		TBlock b = logicOp==null || this.conditions.size()==0
				? new TBlock(c)
						: new TBlock(logicOp, c);
		this.conditions.add(b);
	}

	public <T> void addOrderBy(String field) {
		if(this.ordenations==null)
			this.ordenations = new ArrayList<>();
		TField f = new TField(field);
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
	
}
