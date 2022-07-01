/**
 * 
 */
package com.tedros.ejb.base.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Davis Gordon
 *
 */
@MappedSuperclass
public class TReceptiveEntity extends TEntity implements ITIntegrable {

	private static final long serialVersionUID = -2079872072687921884L;
	
	@Column
	private Long integratedEntityId;
	
	@Column(length=1512)
	private String integratedModelView;
	

	@Column(length=1512)
	private String integratedEntity;

	@Column(length=2512)
	private String integratedModulePath;
	
	@Column(length=512)
	private String integratedViewName;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date integratedDate;

	@Column(length=200)
	private String integratedAppUUID;
	/**
	 * 
	 */
	public TReceptiveEntity() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 * @param versionNum
	 * @param lastUpdate
	 * @param insertDate
	 */
	public TReceptiveEntity(Long id, Integer versionNum, Date lastUpdate, Date insertDate) {
		super(id, versionNum, lastUpdate, insertDate);
	}

	/**
	 * @return the integratedEntityId
	 */
	public Long getIntegratedEntityId() {
		return integratedEntityId;
	}

	/**
	 * @param integratedEntityId the integratedEntityId to set
	 */
	public void setIntegratedEntityId(Long integratedEntityId) {
		this.integratedEntityId = integratedEntityId;
	}

	/**
	 * @return the integratedModelView
	 */
	public String getIntegratedModelView() {
		return integratedModelView;
	}

	/**
	 * @param integratedModelView the integratedModelView to set
	 */
	public void setIntegratedModelView(String integratedModelView) {
		this.integratedModelView = integratedModelView;
	}

	/**
	 * @return the integratedModulePath
	 */
	public String getIntegratedModulePath() {
		return integratedModulePath;
	}

	/**
	 * @param integratedModulePath the integratedModulePath to set
	 */
	public void setIntegratedModulePath(String integratedModulePath) {
		this.integratedModulePath = integratedModulePath;
	}

	/**
	 * @return the integratedDate
	 */
	public Date getIntegratedDate() {
		return integratedDate;
	}

	/**
	 * @param integratedDate the integratedDate to set
	 */
	public void setIntegratedDate(Date integratedDate) {
		this.integratedDate = integratedDate;
	}

	/**
	 * @return the integratedAppUUID
	 */
	public String getIntegratedAppUUID() {
		return integratedAppUUID;
	}

	/**
	 * @param integratedAppUUID the integratedAppUUID to set
	 */
	public void setIntegratedAppUUID(String integratedAppUUID) {
		this.integratedAppUUID = integratedAppUUID;
	}

	/**
	 * @return the integratedViewName
	 */
	public String getIntegratedViewName() {
		return integratedViewName;
	}

	/**
	 * @param integratedViewName the integratedViewName to set
	 */
	public void setIntegratedViewName(String integratedViewName) {
		this.integratedViewName = integratedViewName;
	}

	/**
	 * @return the integratedEntity
	 */
	public String getIntegratedEntity() {
		return integratedEntity;
	}

	/**
	 * @param integratedEntity the integratedEntity to set
	 */
	public void setIntegratedEntity(String integratedEntity) {
		this.integratedEntity = integratedEntity;
	}
}
