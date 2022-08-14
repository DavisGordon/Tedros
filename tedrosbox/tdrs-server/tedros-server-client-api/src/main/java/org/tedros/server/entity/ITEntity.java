package org.tedros.server.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.tedros.server.model.ITModel;

public interface ITEntity extends ITModel, Serializable{

	abstract public Long getId();
	
	abstract public void setId(Long id);
	
	public boolean isNew();
	
	public Integer getVersionNum();

	public void setVersionNum(Integer versionNum);
	
	public Date getLastUpdate();

	public void setLastUpdate(Date lastUpdate);

	public Date getInsertDate();

	public void setInsertDate(Date insertDate);
	
	public List<String> getOrderBy();
	
	public void addOrderBy(String fieldName);
	
	public void setOrderBy(List<String> orders);
	
}
