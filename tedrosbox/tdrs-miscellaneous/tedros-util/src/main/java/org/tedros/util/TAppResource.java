/**
 * 
 */
package org.tedros.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;

/**
 * An app resource helper to copy files 
 * in the package to the external Tedros 
 * module.
 * 
 * @author Davis Gordon
 */
public abstract class TAppResource {

	private String folder;
	private String[] arr = new String[0];
	/**
	 * 
	 */
	public TAppResource(String moduleFolder) {
		this.folder = moduleFolder;
	}
	
	public String getFolderPath() {
		return TedrosFolder.MODULE_FOLDER.getFullPath()+folder+File.separator;
	}
	
	public void addResource(String... name) {
		arr = ArrayUtils.addAll(arr, name);
	}
	
	public  void copyToFolder() {
		if(arr.length == 0)
			return;
		
		for(String ref : arr) {
			File f = new File(this.getFolderPath()+ref);
			if(!f.isFile()) {
				try(InputStream is = this.getClass().getResourceAsStream(ref)){
					FileUtils.copyInputStreamToFile(is, f);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

}
