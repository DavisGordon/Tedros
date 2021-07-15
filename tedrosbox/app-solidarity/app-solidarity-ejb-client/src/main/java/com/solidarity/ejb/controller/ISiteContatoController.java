package com.solidarity.ejb.controller;

import javax.ejb.Remote;

import com.solidarity.model.SiteContato;
import com.tedros.ejb.base.controller.ITEjbController;

@Remote
public interface ISiteContatoController extends ITEjbController<SiteContato>{

}
