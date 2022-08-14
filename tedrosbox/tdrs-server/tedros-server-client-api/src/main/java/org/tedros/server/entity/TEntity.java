package org.tedros.server.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.annotations.CacheCoordinationType;
import org.eclipse.persistence.annotations.CacheType;

@Cache(
	type=CacheType.WEAK, // Cache everything until the JVM decides memory is low.
	size=64000,  // Use 64,000 as the initial cache size.
	expiry=360000,  // 6 minutes
	coordinationType=CacheCoordinationType.INVALIDATE_CHANGED_OBJECTS  
	// if cache coordination is used, only send invalidation messages.
)
@MappedSuperclass
public  class TEntity implements ITEntity {

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
	
	@Transient
	private List<String> orderBy;
	
	public TEntity() {
		addOrderBy("id");
	}
	
	public TEntity(Long id, Integer versionNum, Date lastUpdate, Date insertDate) {
		super();
		this.id = id;
		this.versionNum = versionNum;
		this.lastUpdate = lastUpdate;
		this.insertDate = insertDate;
		addOrderBy("id");
	}
	
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

	@Override
	public List<String> getOrderBy() {
		return orderBy;
	}

	@Override
	public void addOrderBy(String fieldName) {
		if(orderBy==null)
			orderBy = new ArrayList<>();
		orderBy.add(fieldName);
		
	}

	@Override
	public void setOrderBy(List<String> orders) {
		this.orderBy = orders;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TEntity other = (TEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

		

}
