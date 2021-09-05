package com.covidsemfome.ejb.controller;

import javax.ejb.Remote;

import com.covidsemfome.model.SiteContato;
import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface ISiteContatoController extends ITSecureEjbController<SiteContato>{

}
