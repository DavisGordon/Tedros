package org.tedros.core.security.model;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.tedros.core.domain.DomainSchema;
import org.tedros.core.domain.DomainTables;
import org.tedros.server.entity.TVersionEntity;

@Entity
@Table(name = DomainTables.profile, schema = DomainSchema.tedros_core)
public class TProfile extends TVersionEntity {

	private static final long serialVersionUID = -5789123411402469912L;
	
	@Column(name = "name", length=100, nullable = false)
	private String name;
	
	@Column(name = "description", length=600, nullable = true)
	private String description;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(	name=DomainTables.profile_authorization,
				schema=DomainSchema.tedros_core,
				uniqueConstraints=@UniqueConstraint(name="profAuthUK", 
				columnNames = { "profile_id","auth_id" }),
				joinColumns= @JoinColumn(name="profile_id"),
				inverseJoinColumns= @JoinColumn(name="auth_id"))
	private List<TAuthorization> autorizations;
	
	
	public TProfile() {
		
	}
	
	public TProfile(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public TProfile(Long id, String name, String description) {
		setId(id);
		this.name = name;
		this.description = description;
	}
	
	public TProfile(Long id, String name) {
		setId(id);
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<TAuthorization> getAutorizations(String securityId) {
		return autorizations!=null ? autorizations.parallelStream().filter(e -> {
			return e.getSecurityId().equals(securityId);
		}).collect(Collectors.toList()) : new ArrayList<>();
	}

	public List<TAuthorization> getAutorizations() {
		return autorizations;
	}

	public void setAutorizations(List<TAuthorization> autorizations) {
		this.autorizations = autorizations;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if(name!=null) 
			sb.append(name).append(" : ");
		
		if(autorizations!=null) 
			sb.append(autorizations.toString());
		else
			sb.append("autorizations is null");
		
		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((autorizations == null) ? 0 : autorizations.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (super.equals(obj))
			return true;
		if (!(obj instanceof TProfile))
			return false;
		TProfile other = (TProfile) obj;
		if (autorizations == null) {
			if (other.autorizations != null)
				return false;
		} else if (!autorizations.equals(other.autorizations))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
