package com.tedros.fxapi.domain;

public enum TToolBarEnum {
	IPHONE_TOOLBAR ("-fx-background-color: linear-gradient(#98a8bd 0%, #8195af 25%, #6d86a4 100%); ");
	
	private String style; 
	
	private TToolBarEnum(String style) {
		this.style = style;
	}
	
	@Override
	public String toString() {
		return style;
	}
}
