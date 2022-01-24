package org.somos.ejb.controller;

import javax.ejb.Remote;

import org.somos.model.SiteAbout;

import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface ISiteAboutController extends ITSecureEjbController<SiteAbout>{

}
