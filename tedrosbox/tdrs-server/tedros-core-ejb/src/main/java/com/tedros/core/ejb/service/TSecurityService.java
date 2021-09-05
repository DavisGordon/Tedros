/**
 * 
 */
package com.tedros.core.ejb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.commons.collections.CollectionUtils;

import com.tedros.core.security.model.TUser;
import com.tedros.ejb.base.security.TAccessToken;

/**
 * @author Davis Gordon
 *
 */
@Singleton
@Startup
@Lock(LockType.READ) 
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class TSecurityService {

	private List<TUser> repository;
	
	@PostConstruct
	public void init() {
		this.repository = new ArrayList<>();
	}
	
	public boolean isAssigned(TAccessToken token) {
		return CollectionUtils.exists(repository, e -> {
			return ((TUser)e).getAccessToken().equals(token);
		});
	}
	
	@Lock(LockType.WRITE)
	public void remove(TAccessToken token) {
		this.repository.removeIf(e -> {
			return e.getAccessToken().equals(token);
		});
	}

	@Lock(LockType.WRITE) 
	public void addUser(TUser user) {
		if(this.repository.contains(user))
			this.repository.remove(user);
		if(user.getAccessToken()==null)
			user.setAccessToken(new TAccessToken(UUID.randomUUID().toString()));
		this.repository.add(user);
	}
	
}
