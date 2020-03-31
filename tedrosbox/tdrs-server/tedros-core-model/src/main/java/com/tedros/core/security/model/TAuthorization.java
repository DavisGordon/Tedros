/**
 * 
 */
package com.tedros.core.security.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tedros.core.domain.DomainSchema;
import com.tedros.core.domain.DomainTables;
import com.tedros.ejb.base.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.authorization, schema = DomainSchema.tedros_core)
public class TAuthorization extends TEntity {
	
	private static final long serialVersionUID = 3480135875409356712L;
	
	@ManyToOne
	@JoinColumn(name = "prof_id", nullable=false)
	private TProfile profile;
	
	@Column(name="security_id", length=200, nullable=false)
	private String securityId;
	
	@Column(name="type", length=30, nullable=false)
	private String type;
	
	@Column(name="enabled", length=1, nullable=true)
	private String enabled;
	
	@Transient
	private String appName;
	
	@Transient
	private String moduleName;
	
	@Transient
	private String viewName;
	
	@Transient
	private String typeDescription;
	
		
	public String getSecurityId() {
		return securityId;
	}

	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	public TProfile getProfile() {
		return profile;
	}

	public void setProfile(TProfile profile) {
		this.profile = profile;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, true);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, true);
	}
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		if(securityId!=null) 
			sb.append(securityId).append(" - ");
		
		if(type!=null) 
			sb.append(type).append(" - ");
		
		if(enabled!=null) 
			sb.append(enabled);
		else
			sb.append("enabled is null");
		
		
		return sb.toString();
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public String getTypeDescription() {
		return typeDescription;
	}

	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
	}

}
