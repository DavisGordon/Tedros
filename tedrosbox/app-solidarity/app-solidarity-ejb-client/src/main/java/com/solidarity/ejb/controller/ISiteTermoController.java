package com.solidarity.ejb.controller;

import javax.ejb.Remote;

import com.solidarity.model.SiteTermo;
import com.tedros.ejb.base.controller.ITEjbController;

@Remote
public interface ISiteTermoController extends ITEjbController<SiteTermo>{

}
