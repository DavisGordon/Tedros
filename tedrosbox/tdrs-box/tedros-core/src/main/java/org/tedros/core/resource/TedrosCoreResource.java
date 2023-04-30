/**
 * 
 */
package org.tedros.core.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.tedros.util.TedrosFolder;

/**
 * @author Davis Gordon
 *
 */
public class TedrosCoreResource {

	private static final String FOLDER = "TEDROS_CORE";
	public static final String APP_MODULE_PATH = TedrosFolder.MODULE_FOLDER.getFullPath()+FOLDER+File.separator;
	private static final String IMAGES_JASPER ="core_image_rep.jasper";
	
	private static final String[] FILES = {
			IMAGES_JASPER, "core_image_rep.jrxml",
			"core_image_subrep.jasper", "core_image_subrep.jrxml"
			};
	/**
	 * 
	 */
	public TedrosCoreResource() {
	}

	public static InputStream getImagesJasperInputStream() throws FileNotFoundException {
		File f = new File(APP_MODULE_PATH+IMAGES_JASPER);
		if(f.isFile()) {
			return new FileInputStream(f);
		}
		
		return null;
	}
	
	public static void createResource() {
		for(String ref : FILES) {
			File f = new File(APP_MODULE_PATH+ref);
			if(!f.isFile()) {
				try(InputStream is = TedrosCoreResource.class.getResourceAsStream(ref)){
					FileUtils.copyInputStreamToFile(is, f);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	

}
