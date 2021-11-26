package com.tedros.ejb.base.entity;

import com.tedros.ejb.base.model.ITFileBaseModel;

public interface ITFileEntity extends ITFileBaseModel, ITEntity {
	
	public ITByteEntity getByteEntity();
	public void setByteEntity(ITByteEntity byteEntity);
	public String getDescription();
	public void setDescription(String description);
	public String getOwner();
	public void setOwner(String owner);
	
	
}
