package org.tedros.core.controller;

import jakarta.ejb.Remote;

import org.tedros.common.model.TMimeType;
import org.tedros.server.controller.ITSecureEjbController;

@Remote
public interface TMimeTypeController extends ITSecureEjbController<TMimeType>{


	static final String JNDI_NAME = "TMimeTypeControllerRemote";
}
