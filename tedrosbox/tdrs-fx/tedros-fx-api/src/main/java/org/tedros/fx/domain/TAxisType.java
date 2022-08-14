package org.tedros.fx.domain;

public enum TAxisType {

	NUMBER (Number.class),
	STRING (String.class),
	DATE   (String.class);
	
	private Class<?> value;
	
	private TAxisType(Class<?> clazz){
		this.value = clazz;
	}
	
	public Class<?> getValue() {
		return value;
	}
}
