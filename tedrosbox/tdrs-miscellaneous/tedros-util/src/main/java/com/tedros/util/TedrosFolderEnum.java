package com.tedros.util;

import java.io.File;

public enum TedrosFolderEnum {

	ROOT_FOLDER 		("Tedros" + File.separator),
	IMAGES_FOLDER 		("IMAGES" + File.separator),
	BACKGROUND_IMAGES_FOLDER ("IMAGES" + File.separator + "FUNDO" + File.separator),
	CONF_FOLDER 		("CONF" + File.separator),
	CSS_CASPIAN_FOLDER 		("css" + File.separator + "caspian" + File.separator),
	DATA_FILE_FOLDER 	("DATA_FILE" + File.separator),
	MODULE_FOLDER 		("MODULE" + File.separator),
	LOG_FOLDER			("LOG" + File.separator),
	EXPORT_FOLDER			("EXPORT" + File.separator);
	
	private String folder;
	
	private TedrosFolderEnum(String folder){
		this.folder = folder;
	}

	public String getFolder() {
		return folder;
	}
	
	
}
