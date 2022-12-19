package org.tedros.server.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public  class TVersionEntity extends TEntity implements ITVersionableEntity {

	private static final long serialVersionUID = -5430421399490550671L;
	
	@Version
    @Column(name="OPTLOCK")
	private Integer versionNum;
	
	
	public TVersionEntity() {
		super();
	}
	
	public TVersionEntity(Long id, Integer versionNum, Date lastUpdate, Date insertDate) {
		super(id, lastUpdate, insertDate);
		this.versionNum = versionNum;
	}
	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((versionNum == null) ? 0 : versionNum.hashCode());
		return result;
	}

}
