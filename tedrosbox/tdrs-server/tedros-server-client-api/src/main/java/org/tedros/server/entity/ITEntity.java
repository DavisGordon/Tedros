package org.tedros.server.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.tedros.server.model.ITModel;

public interface ITEntity extends ITModel, Serializable{

	public abstract Long getId();
	
	public abstract void setId(Long id);
	
	boolean isNew();
	
	Date getLastUpdate();

	void setLastUpdate(Date lastUpdate);

	Date getInsertDate();

	void setInsertDate(Date insertDate);
	
	Long getCreatedByUserId();

	void setCreatedByUserId(Long createdByUserId);
	
	List<String> getOrderBy();
	
	void addOrderBy(String fieldName);
	
	void setOrderBy(List<String> orders);
	
}
