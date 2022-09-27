package org.tedros.chat.entity;

public enum TStatus {

	SENT ("#{label.sent}"),
	RECEIVED ("#{label.received}"),
	READED ("#{label.readed}");
	
	private String value;
	
	private TStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	
}
