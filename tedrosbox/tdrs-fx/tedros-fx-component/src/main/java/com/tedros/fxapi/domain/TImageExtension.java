/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 21/03/2014
 */
package com.tedros.fxapi.domain;

import org.apache.commons.lang3.ArrayUtils;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public enum TImageExtension {

	ALL_IMAGES ("*.jpg", "*.jpeg", "*.bmp", "*.gif", "*.tif", "*.png"), 
	JPG ("*.jpg", "*.jpeg"),  
	PNG ("*.png"), 
	BMP ("*.bmp"), 
	GIF ("*.gif"),
	TIF ("*.tif");
	
	private String[] extension;
	
	private TImageExtension(String... extension) {
		this.extension = extension;
	}
	
	public String[] getExtension() {
		return extension;
	}
	
	public String[] getExtensionName() {
		String[] l = new String[] {};
		for(String s : extension)
			l = ArrayUtils.add(l, s.replace("*.", ""));
		return l;
	}
	
}
