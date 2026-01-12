package org.tedros;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.tedros.util.TFileUtil;
import org.tedros.util.TLoggerUtil;
import org.tedros.util.TZipUtil;

class TedrosFolderHelper {
	
	private TedrosFolderHelper() {
	}
	
	static void checkTedrosFolder() {
		try {
        	String outputFolder = System.getProperty("user.home");
        	boolean extract = TedrosFolderHelper.checkAndBuildTedrosBoxFolder(outputFolder);
			if(extract)
				TedrosFolderHelper.extractZip(outputFolder);
		} catch (IOException e) {
			TLoggerUtil.error(TedrosBox.class, e.toString(), e);
		}
	}
	
	private static boolean checkAndBuildTedrosBoxFolder(String outputFolder) throws IOException{
		
		//create tedros directory if is not exists
    	File folder = new File(outputFolder+"/.tedros");
    	if(folder.exists()){ 
    		if(new File(outputFolder+"/.tedros"+"/tedrosbox__V"+TedrosRelease.version+".txt").exists())
    			return false;
    		TFileUtil.delete(folder);
    	}
    	folder.mkdir();
    	new File(outputFolder+"/.tedros/LOG").mkdir();
    	return true;
	}

	private static void extractZip(String outputFolder) {
		try(InputStream zipFile = TedrosRelease.class.getResourceAsStream("TedrosBox.zip")){
			TZipUtil.unZip(zipFile, outputFolder);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
