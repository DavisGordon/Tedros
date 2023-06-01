/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 21/03/2014
 */
package org.tedros.fx.domain;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public enum TFileExtension {

	ALL_FILES ("*.*"),
	ALL_IMAGES ("*.*", "*.jpg", "*.jpeg", "*.bmp", "*.gif", "*.tif", "*.png"), 
	ALL_MICROSOFT_WORD ("*.doc", "*.docx", "*.docm", "*.dot", "*.dotx", "*.dotm"),
	ALL_MICROSOFT_EXCEL ("*.xls", "*.xlsx", "*.xlsm", "*.xlt", "*.xltx", "*.xltm"),
	DOC ("*.doc"),
	DOCX ("*.docx"),
	JPG ("*.jpg", "*.jpeg"),  
	PNG ("*.png"), 
	BMP ("*.bmp"), 
	GIF ("*.gif"),
	TIF ("*.tif"),TXT ("*.txt"), 
	PDF ("*.pdf"), 
	CSV ("*.csv"),
	XLS ("*.xls"),
	XLSX ("*.xlsx");
	
	private String[] extension;
	
	private TFileExtension(String... extension) {
		this.extension = extension;
	}
	
	public String[] getExtension() {
		return extension;
	}
	
}
