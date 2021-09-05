package com.covidsemfome.ejb.controller;

import javax.ejb.Remote;

import com.covidsemfome.model.SiteTermo;
import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface ISiteTermoController extends ITSecureEjbController<SiteTermo>{

}
