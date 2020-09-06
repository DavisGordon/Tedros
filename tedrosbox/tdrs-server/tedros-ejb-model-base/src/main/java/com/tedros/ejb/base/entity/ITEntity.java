package com.tedros.ejb.base.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.tedros.ejb.base.model.ITModel;

public interface ITEntity extends ITModel, Serializable{

	abstract public Long getId();
	
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
