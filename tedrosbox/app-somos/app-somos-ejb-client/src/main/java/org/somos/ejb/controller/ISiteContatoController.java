package org.somos.ejb.controller;

import javax.ejb.Remote;

import org.somos.model.SiteContato;

import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface ISiteContatoController extends ITSecureEjbController<SiteContato>{

}
