package org.tedros.server.query;

public class TCondition<T> extends TField{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5307089551440177848L;
	private TCompareOp operator;
	private T value;
	/**
	 * @param field
	 * @param value
	 */
	public TCondition(String field, T value) {
		super(field);
		this.value = value;
	}
	/**
	 * @param field
	 * @param operator
	 * @param value
	 */
	public TCondition(String field, TCompareOp operator, T value) {
		super(field);
		this.operator = operator;
		this.value = value;
	}
	/**
	 * @return the operator
	 */
	public TCompareOp getOperator() {
		return operator;
	}
	/**
	 * @return the value
	 */
	public T getValue() {
		return value;
	}
}