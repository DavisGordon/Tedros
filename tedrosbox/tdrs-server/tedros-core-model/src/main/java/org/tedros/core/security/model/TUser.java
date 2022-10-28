package org.tedros.core.security.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.tedros.core.domain.DomainSchema;
import org.tedros.core.domain.DomainTables;
import org.tedros.server.entity.ITUser;
import org.tedros.server.entity.TEntity;
import org.tedros.server.security.TAccessToken;

@Entity
@Table(name = DomainTables.user, schema = DomainSchema.tedros_core)
public class TUser extends TEntity implements ITUser {
	
	private static final long serialVersionUID = 2125379739952921937L;

	@Column(name = "name", length=100, nullable = false)
	private String name;
	
	@Column(name = "login", length=100, nullable = false)
	private String login;
	
	@Column(name = "password", length=250, nullable = false)
	private String password;
	
	@Column(name = "active", length=1)
	private String active;
	
	@Column(length=1)
	private String accessLogEnable;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(	name=DomainTables.user_profile,
				schema=DomainSchema.tedros_core,
				joinColumns= @JoinColumn(name="user_id"),
				inverseJoinColumns= @JoinColumn(name="prof_id"))
	private Set<TProfile> profiles;
	
	@ManyToOne
	@JoinColumn(name="prof_id")
	private TProfile activeProfile;
	
	private TAccessToken accessToken;
	
	
	public TUser() {
		
	}
	
	public TUser(String name, String login) {
		this.name = name;
		this.login = login;
	}
	
	public TUser(String name) {
		this.name = name;
	}
			
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj==null)
			return false;
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		TUser other = (TUser) obj;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		return true;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}

	/* (non-Javadoc)
	 * @see org.tedros.core.security.model.ITUser#getName()
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see org.tedros.core.security.model.ITUser#getAccessToken()
	 */
	@Override
	public TAccessToken getAccessToken() {
		return accessToken;
	}

	/**
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(TAccessToken accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * @return the accessLogEnable
	 */
	public String getAccessLogEnable() {
		return accessLogEnable;
	}

	/**
	 * @param accessLogEnable the accessLogEnable to set
	 */
	public void setAccessLogEnable(String accessLogEnable) {
		this.accessLogEnable = accessLogEnable;
	}

	/* (non-Javadoc)
	 * @see org.tedros.core.security.model.ITUser#getProfilesText()
	 */
	@Override
	public String getProfilesText() {
		StringBuffer sb = new StringBuffer();
		getProfiles().forEach(p->{
			sb.append((sb.toString().isEmpty() ? "" : ", ")+ p.getName());
		});
		return sb.toString();
	}

}
