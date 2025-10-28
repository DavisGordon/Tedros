package org.tedros.server.model;

import java.io.File;
import java.io.IOException;

public interface ITFileModel extends ITFileBaseModel, ITModel {
	
	public File getFile()  throws IOException;
	public void setFile(File file) throws IOException;
	
	public ITByteModel getByteModel();
	public void setByteModel(ITByteModel byteModel);
	
	public String getFilePath();
	public void setFilePath(String filePath);
	
}
