package com.tedros.core.ejb.service;

import javax.ejb.Local;
import javax.ejb.Remote;

import com.tedros.core.security.model.TProfile;
import com.tedros.ejb.base.service.ITEjbService;

@Remote
@Local
public interface TProfileService extends ITEjbService<TProfile>{

	
}
