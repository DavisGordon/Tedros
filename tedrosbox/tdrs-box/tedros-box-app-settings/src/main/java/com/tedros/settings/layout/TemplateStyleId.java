package com.tedros.settings.layout;

public enum TemplateStyleId {

	BACKGROUND_COLOR ("-fx-background-color: "),
	TEXT_FILL ("-fx-text-fill: "),
	BORDER_COLOR ("-fx-border-color: ");

	private String id;
	private TemplateStyleId(String styleId) {
		this.id = styleId;
	}
	
	@Override
	public String toString() {
		return id;
	}
	
}
