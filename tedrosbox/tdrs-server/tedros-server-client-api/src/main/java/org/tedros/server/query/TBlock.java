/**
 * 
 */
package org.tedros.server.query;

import java.io.Serializable;

/**
 * @author Davis Gordon
 *
 */
public class TBlock implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5156295347187302751L;
	private TLogicOp operator;
	private TCondition<?> condition;
	/**
	 * @param condition
	 */
	public TBlock(TCondition<?> condition) {
		this.condition = condition;
	}
	/**
	 * @param operator
	 * @param condition
	 */
	public TBlock(TLogicOp operator, TCondition<?> condition) {
		this.operator = operator;
		this.condition = condition;
	}
	/**
	 * @return the operator
	 */
	public TLogicOp getOperator() {
		return operator;
	}
	/**
	 * @return the condition
	 */
	public TCondition<?> getCondition() {
		return condition;
	}
	
	
}
