package com.covidsemfome.ejb.controller;

import javax.ejb.Remote;

import com.covidsemfome.model.SiteAbout;
import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface ISiteAboutController extends ITSecureEjbController<SiteAbout>{

}
