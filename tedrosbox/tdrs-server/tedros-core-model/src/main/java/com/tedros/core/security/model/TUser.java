package com.tedros.core.security.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tedros.core.domain.DomainSchema;
import com.tedros.core.domain.DomainTables;
import com.tedros.ejb.base.entity.TEntity;

@Entity
@Table(name = DomainTables.user, schema = DomainSchema.tedros_core)
public class TUser extends TEntity {
	
	private static final long serialVersionUID = 2125379739952921937L;

	@Column(name = "name", length=100, nullable = false)
	private String name;
	
	@Column(name = "login", length=100, nullable = false)
	private String login;
	
	@Column(name = "password", length=250, nullable = false)
	private String password;
	
	@Column(name = "active", length=1)
	private String active;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(	name=DomainTables.user_profile,
				schema=DomainSchema.tedros_core,
				joinColumns= @JoinColumn(name="user_id"),
				inverseJoinColumns= @JoinColumn(name="prof_id"))
	private Set<TProfile> profiles;
	
	@ManyToOne
	@JoinColumn(name="prof_id")
	private TProfile activeProfile;
	
	public TUser() {
		
	}
	
	public TUser(String name, String login) {
		this.name = name;
		this.login = login;
	}
	
	public TUser(String name) {
		this.name = name;
	}
			
	@Override
	public boolean equals(Object obj) {		
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public Set<TProfile> getProfiles() {
		return profiles;
	}

	public void setProfiles(Set<TProfile> profiles) {
		this.profiles = profiles;
	}

	public TProfile getActiveProfile() {
		return activeProfile;
	}

	public void setActiveProfile(TProfile activeProfile) {
		this.activeProfile = activeProfile;
	}

}
