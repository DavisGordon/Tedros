package org.tedros.core.controller;

import jakarta.ejb.Remote;

import org.tedros.server.controller.ITEjbChartController;

@Remote
public interface TProfileChartController extends ITEjbChartController{


	static final String JNDI_NAME = "TProfileChartControllerRemote";
}
