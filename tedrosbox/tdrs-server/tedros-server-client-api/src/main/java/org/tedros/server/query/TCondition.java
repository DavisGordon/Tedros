package org.tedros.server.query;

public class TCondition<T> extends TField{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5307089551440177848L;
	private TCompareOp operator;
	private T value;
	private boolean dynamicValue;
	/**
	 * @param field
	 * @param value
	 */
	public TCondition(String field, T value) {
		super(TSelect.ALIAS, field);
		this.value = value;
	}
	/**
	 * @param alias
	 * @param field
	 * @param value
	 */
	public TCondition(String alias, String field, T value) {
		super(alias, field);
		this.value = value;
	}
	/**
	 * @param field
	 * @param operator
	 * @param value
	 */
	public TCondition(String field, TCompareOp operator, T value) {
		super(TSelect.ALIAS, field);
		this.operator = operator;
		this.value = value;
	}
	/**
	 * @param alias
	 * @param field
	 * @param operator
	 * @param value
	 */
	public TCondition(String alias, String field, TCompareOp operator, T value) {
		super(alias, field);
		this.operator = operator;
		this.value = value;
	}
	
	
	/**
	 * @param alias
	 * @param field
	 * @param operator
	 * @param value
	 * @param dynamicValue
	 */
	public TCondition(String alias, String field, TCompareOp operator, T value, boolean dynamicValue) {
		super(alias, field);
		this.operator = operator;
		this.value = value;
		this.dynamicValue = dynamicValue;
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
	/**
	 * @param value the value to set
	 */
	public void setValue(T value) {
		this.value = value;
	}
	/**
	 * @return the dynamicValue
	 */
	public boolean isDynamicValue() {
		return dynamicValue;
	}
	/**
	 * @param dynamicValue the dynamicValue to set
	 */
	public void setDynamicValue(boolean dynamicValue) {
		this.dynamicValue = dynamicValue;
	}
}