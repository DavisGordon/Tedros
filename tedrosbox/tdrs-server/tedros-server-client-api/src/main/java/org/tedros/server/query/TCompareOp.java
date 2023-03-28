package org.tedros.server.query;

public enum TCompareOp {

	EQUAL("="), NOT_EQ("!="), 
	LIKE("like"), LIKE_CASE_SENSITIVE ("like"),
	LESS_THAN("<"), LESS_EQ_THAN("<="),
	GREATER_THAN(">"), GREATER_EQ_THAN(">=");
	
	private String value;

	/**
	 * @param value
	 */
	private TCompareOp(String value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
}
