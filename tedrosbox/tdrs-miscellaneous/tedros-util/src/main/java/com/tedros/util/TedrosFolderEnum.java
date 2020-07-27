package com.tedros.util;

public enum TedrosFolderEnum {

	ROOT_FOLDER 		("Tedros/"),
	IMAGES_FOLDER 		("IMAGES/"),
	BACKGROUND_IMAGES_FOLDER ("IMAGES/FUNDO/"),
	CONF_FOLDER 		("CONF/"),
	DATA_FILE_FOLDER 	("DATA_FILE/"),
	MODULE_FOLDER 		("MODULE/"),
	LOG_FOLDER			("LOG/"),
	EXPORT_FOLDER			("EXPORT/");
	
	private String folder;
	
	private TedrosFolderEnum(String folder){
		this.folder = folder;
	}

	public String getFolder() {
		return folder;
	}
	
	
}
