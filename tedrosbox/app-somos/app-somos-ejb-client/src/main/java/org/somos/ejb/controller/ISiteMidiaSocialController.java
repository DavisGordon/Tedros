package org.somos.ejb.controller;

import javax.ejb.Remote;

import org.somos.model.SiteMidiaSocial;

import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface ISiteMidiaSocialController extends ITSecureEjbController<SiteMidiaSocial>{

}
