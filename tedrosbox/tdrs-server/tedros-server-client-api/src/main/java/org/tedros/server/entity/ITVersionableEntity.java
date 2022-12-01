package org.tedros.server.entity;

public interface ITVersionableEntity extends ITEntity{

	public Integer getVersionNum();

	public void setVersionNum(Integer versionNum);
	
}
