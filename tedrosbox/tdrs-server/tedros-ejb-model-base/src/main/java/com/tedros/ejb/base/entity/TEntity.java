package com.tedros.ejb.base.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;


@MappedSuperclass
public abstract class TEntity implements ITEntity {

	private static final long serialVersionUID = 4360077147058078710L;
	
	@Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    @Basic(optional = false)  
    @Column(name = "id", nullable = false)
	private Long id;
	
	@Version
    @Column(name="OPTLOCK")
	private Integer versionNum;
	
	@Column(name = "last_update", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdate;
	
	@Column(name = "insert_date", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date insertDate;
	
	public boolean isNew(){
		return null==getId();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

		

}
