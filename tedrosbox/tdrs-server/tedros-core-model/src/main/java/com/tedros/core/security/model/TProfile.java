package com.tedros.core.security.model;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tedros.core.domain.DomainSchema;
import com.tedros.core.domain.DomainTables;
import com.tedros.ejb.base.entity.TEntity;

@Entity
@Table(name = DomainTables.profile, schema = DomainSchema.tedros_core)
public class TProfile extends TEntity {

	private static final long serialVersionUID = -5789123411402469912L;
	
	@Column(name = "name", length=100, nullable = false)
	private String name;
	
	@Column(name = "description", length=600, nullable = true)
	private String description;
	
	@OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
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

	public List<TAuthorization> getAutorizations() {
		return autorizations;
	}

	public void setAutorizations(List<TAuthorization> autorizations) {
		this.autorizations = autorizations;
	}
	
}
