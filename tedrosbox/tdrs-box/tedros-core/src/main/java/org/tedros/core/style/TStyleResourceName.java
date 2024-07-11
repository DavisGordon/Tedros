package org.tedros.core.style;

/**
 * The enum with all external tedros resources names
 * */
public enum TStyleResourceName {

	DEFAULT_STYLE ("default-style.properties"),
	CUSTOM_STYLE ("custom-style.properties"),
	PANEL_CUSTOM_STYLE ("panel-custom-style.properties"),
	BACKGROUND_STYLE ("background-style.properties"),
	HEADER_STYLE ("header-style.properties"),
	THEME ("theme.properties"),
	LANGUAGE ("language.properties");
	
	private String prop;
	
	private TStyleResourceName(String prop) {
		this.prop = prop;
	}
	
	@Override
	public String toString() {
		return prop;
	}
	
	
}
