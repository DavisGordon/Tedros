/**
 * 
 */
package org.tedros.core.security.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.tedros.core.domain.DomainSchema;
import org.tedros.core.domain.DomainTables;
import org.tedros.server.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.authorization, schema = DomainSchema.tedros_core, 
uniqueConstraints=@UniqueConstraint(name="aSecurityTypeUK", columnNames = { "security_id", "type" }))
public class TAuthorization extends TEntity {
	
	private static final long serialVersionUID = 3480135875409356712L;
	
	@Column(name="security_id", length=200, nullable=false)
	private String securityId;
	
	@Column(name="type", length=30, nullable=false)
	private String type;
	
	@Column(name="app_name", length=60, nullable=false)
	private String appName;
	
	@Column(name="module_name", length=60, nullable=true)
	private String moduleName;
	
	@Column(name="view_name", length=60, nullable=true)
	private String viewName;
	
	@Column(name="type_description", length=200, nullable=false)
	private String typeDescription;
	
	@Column(name="enabled", length=3, nullable=false)
	private String enabled; 
	
		
	/**
	 * 
	 */
	public TAuthorization() {
		super.getOrderBy().clear();
		super.addOrderBy("appName");
		super.addOrderBy("moduleName");
		super.addOrderBy("viewName");
		super.addOrderBy("type");
		
	}

	public String getSecurityId() {
		return securityId;
	}

	public void setSecurityId(String securityId) {
		this.securityId = securityId;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		/*if (!super.equals(obj))
			return false;*/
		if (!(obj instanceof TAuthorization))
			return false;
		TAuthorization other = (TAuthorization) obj;
		if (securityId == null) {
			if (other.securityId != null)
				return false;
		} else if (!securityId.equals(other.securityId))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (securityId != null) {
			builder.append(securityId);
			builder.append(", ");
		}
		if (appName != null) {
			builder.append(appName);
			builder.append(", ");
		}
		if (moduleName != null) {
			builder.append(moduleName);
			builder.append(", ");
		}
		if (viewName != null) {
			builder.append(viewName);
			builder.append(", ");
		}
		if (type != null) {
			builder.append(type);
		}
		return builder.toString();
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

	/**
	 * @return the enabled
	 */
	public String getEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

}
