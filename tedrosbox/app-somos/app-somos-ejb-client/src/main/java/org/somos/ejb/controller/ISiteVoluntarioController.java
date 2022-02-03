package org.somos.ejb.controller;

import javax.ejb.Remote;

import org.somos.model.SiteVoluntario;

import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface ISiteVoluntarioController extends ITSecureEjbController<SiteVoluntario>{

}
