package com.tedros.tools.module.scheme.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.tedros.core.style.TStyleResourceName;
import com.tedros.core.style.TThemeUtil;
import com.tedros.ejb.base.model.ITModel;
import com.tedros.ejb.base.model.TFileModel;
import com.tedros.util.TedrosFolder;

public class TBackgroundImage implements ITModel {

	private TFileModel fileModel;
	
	public TBackgroundImage() {
		String propFilePath = TThemeUtil.getBackgroundFilePath();
		Properties prop = new Properties();
		try {
			InputStream is = new FileInputStream(propFilePath);
			prop.load(is);
			is.close();
			if(StringUtils.isNotBlank(prop.getProperty("image"))){
				String fp = TedrosFolder.BACKGROUND_IMAGES_FOLDER.getFullPath() + prop.getProperty("image");
				File f = new File(fp);
				fileModel = new TFileModel(f);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	
	}

	public final TFileModel getFileModel() {
		return fileModel;
	}

	public final void setFileModel(TFileModel fileModel) {
		this.fileModel = fileModel;
	}

	
	
}
