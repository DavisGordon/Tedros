package org.tedros.server.model;

public interface ITFileBaseModel {
	
	public String getFileName();
	public void setFileName(String name);
	
	public String getFileExtension();
	public void setFileExtension(String extension);
	
	public Long getFileSize();
	public void setFileSize(Long size);
	
	public ITByteBaseModel getByte();
	
	public <T extends ITByteBaseModel> void setByte(T byteModel);
	
	
}
