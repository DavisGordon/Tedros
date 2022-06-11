package com.tedros.core.ejb.controller;

import javax.ejb.Remote;

import com.tedros.common.model.TMimeType;
import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface TMimeTypeController extends ITSecureEjbController<TMimeType>{


	static final String JNDI_NAME = "TMimeTypeControllerRemote";
}
