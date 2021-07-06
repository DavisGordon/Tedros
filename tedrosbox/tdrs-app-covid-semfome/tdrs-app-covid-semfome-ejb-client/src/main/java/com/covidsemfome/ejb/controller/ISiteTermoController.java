package com.covidsemfome.ejb.controller;

import javax.ejb.Remote;

import com.covidsemfome.model.SiteTermo;
import com.tedros.ejb.base.controller.ITEjbController;

@Remote
public interface ISiteTermoController extends ITEjbController<SiteTermo>{

}
