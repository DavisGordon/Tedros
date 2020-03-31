package com.tedros.ejb.base.entity;


public interface ITFileEntity extends ITEntity {
	
	public String getFileName();
	public void setFileName(String name);
	
	public String getFileExtension();
	public void setFileExtension(String extension);
	
	public long getFileSize();
	public void setFileSize(long size);
	
	public ITByteEntity getByteEntity();
	public void setByteEntity(ITByteEntity byteEntity);
	
}
