package org.tedros.core.controller;

import javax.ejb.Remote;

import org.tedros.server.controller.ITEjbChartController;

@Remote
public interface TProfileChartController extends ITEjbChartController{


	static final String JNDI_NAME = "TProfileChartControllerRemote";
}
