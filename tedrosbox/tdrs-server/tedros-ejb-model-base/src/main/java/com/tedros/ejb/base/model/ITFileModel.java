package com.tedros.ejb.base.model;

import java.io.File;
import java.io.IOException;

public interface ITFileModel extends ITModel {
	
	public File getFile()  throws IOException;
	public void setFile(File file) throws IOException;
	
	public String getFileName();
	public void setFileName(String name);
	
	public String getFileExtension();
	
	public Long getFileSize();
	
	public ITByteModel getByteModel();
	public void setByteModel(ITByteModel byteModel);
	
	public String getFilePath();
	public void setFilePath(String filePath);
	
}
