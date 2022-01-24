package org.somos.ejb.controller;

import javax.ejb.Remote;

import org.somos.model.SiteTermo;

import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface ISiteTermoController extends ITSecureEjbController<SiteTermo>{

}
