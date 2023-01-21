/**
 * 
 */
package org.tedros.core.ejb.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.commons.collections.CollectionUtils;
import org.tedros.core.security.model.TAuthorization;
import org.tedros.core.security.model.TProfile;
import org.tedros.core.security.model.TUser;
import org.tedros.server.security.TAccessToken;

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
	
	public TUser getUser(TAccessToken token) {
		Optional<TUser> op =repository.parallelStream()
				.filter(p->{
					return p.getAccessToken().equals(token);
				})
				.findFirst();
		return op.isPresent() 
				? op.get()
						: null;
	}
	
	public boolean isAssigned(TAccessToken token) {
		return CollectionUtils.exists(repository, e -> {
			return ((TUser)e).getAccessToken().equals(token);
		});
	}
	
	public boolean isActionGranted(TAccessToken token, String securityId, String... action) {
		return CollectionUtils.exists(repository, e -> {
			boolean b = false;
			if( ((TUser)e).getAccessToken().equals(token)) {
				TProfile p = ((TUser)e).getActiveProfile();
				if(p!=null) {
					List<TAuthorization> l = p.getAutorizations(securityId);
					if(l!=null) {
						List<String> actions = Arrays.asList(action);
						b = l.stream()
						.anyMatch(y->{
							return actions.stream().anyMatch(x->{
								return y.getType().trim().equals(x.trim());
							});
						});
					}
				}
			};
			return b;
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
