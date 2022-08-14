package org.tedros.server.entity;

import org.tedros.server.model.ITFileBaseModel;

public interface ITFileEntity extends ITFileBaseModel, ITEntity {
	
	public ITByteEntity getByteEntity();
	public void setByteEntity(ITByteEntity byteEntity);
	public String getDescription();
	public void setDescription(String description);
	public String getOwner();
	public void setOwner(String owner);
	
	
}
