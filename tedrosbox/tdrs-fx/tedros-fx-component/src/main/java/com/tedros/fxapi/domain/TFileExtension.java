/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 21/03/2014
 */
package com.tedros.fxapi.domain;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public enum TFileExtension {

	ALL_FILES ("*.*"),
	ALL_IMAGES ("*.*", "*.jpg", "*.jpeg", "*.bmp", "*.gif", "*.tif", "*.png"), 
	JPG ("*.jpg", "*.jpeg"),  
	PNG ("*.png"), 
	BMP ("*.bmp"), 
	GIF ("*.gif"),
	TIF ("*.tif"),
	ALL_MICROSOFT_WORD ("*.doc", "*.docx", "*.docm", "*.dot", "*.dotx", "*.dotm"),
	ALL_MICROSOFT_EXCEL ("*.xls", "*.xlsx", "*.xlsm", "*.xlt", "*.xltx", "*.xltm"),
	TXT ("*.txt"), 
	PDF ("*.pdf"), 
	CSV ("*.csv");
	
	private String[] extension;
	
	private TFileExtension(String... extension) {
		this.extension = extension;
	}
	
	public String[] getExtension() {
		return extension;
	}
	
}
