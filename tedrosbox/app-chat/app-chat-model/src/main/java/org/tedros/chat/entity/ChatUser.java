/**
 * 
 */
package org.tedros.chat.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.tedros.chat.domain.DomainSchema;
import org.tedros.chat.domain.DomainTables;
import org.tedros.server.entity.TVersionEntity;
import org.tedros.server.security.TAccessToken;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name=DomainTables.user, schema=DomainSchema.schema)
public class ChatUser extends TVersionEntity {

	private static final long serialVersionUID = -7651172023855481688L;
	
	@Column(nullable=false)
	private Long userId;
	
	@Column(length=120, nullable=false)
	private String name;
	
	@Column(nullable=false)
	private String profiles;

	private TAccessToken token;
	
	/**
	 * 
	 */
	public ChatUser() {
	}
	/**
	 * @param id
	 * @param name
	 */
	public ChatUser(Long id, String name, String profiles) {
		this.userId = id;
		this.name = name;
		this.profiles = profiles;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return (name != null ? name : "");
	}
	/**
	 * @return the profiles
	 */
	public String getProfiles() {
		return profiles;
	}
	/**
	 * @param profiles the profiles to set
	 */
	public void setProfiles(String profiles) {
		this.profiles = profiles;
	}
	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((profiles == null) ? 0 : profiles.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof ChatUser))
			return false;
		ChatUser other = (ChatUser) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (profiles == null) {
			if (other.profiles != null)
				return false;
		} else if (!profiles.equals(other.profiles))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
	/**
	 * @return the token
	 */
	public TAccessToken getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(TAccessToken token) {
		this.token = token;
	}
	
	

}
