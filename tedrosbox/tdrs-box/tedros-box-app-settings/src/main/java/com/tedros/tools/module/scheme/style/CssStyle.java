package com.tedros.tools.module.scheme.style;

public enum CssStyle {

	BACKGROUND_COLOR ("-fx-background-color: "),
	TEXT_FILL ("-fx-text-fill: "),
	BORDER_COLOR ("-fx-border-color: ");

	private String id;
	private CssStyle(String styleId) {
		this.id = styleId;
	}
	
	@Override
	public String toString() {
		return id;
	}
	
}
