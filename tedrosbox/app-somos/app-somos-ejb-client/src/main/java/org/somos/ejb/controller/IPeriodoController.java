package org.somos.ejb.controller;

import javax.ejb.Remote;

import org.somos.model.Periodo;

import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface IPeriodoController extends ITSecureEjbController<Periodo>{
	
	
}
