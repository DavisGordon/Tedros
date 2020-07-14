package com.tedros.core.ejb.controller;

import javax.ejb.Remote;

import com.tedros.core.security.model.TProfile;
import com.tedros.ejb.base.controller.ITEjbController;

@Remote
public interface TProfileController extends ITEjbController<TProfile>{

	
}
