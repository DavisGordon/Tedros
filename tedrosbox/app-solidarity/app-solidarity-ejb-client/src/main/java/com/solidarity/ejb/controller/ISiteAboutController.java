package com.solidarity.ejb.controller;

import javax.ejb.Remote;

import com.solidarity.model.SiteAbout;
import com.tedros.ejb.base.controller.ITEjbController;

@Remote
public interface ISiteAboutController extends ITEjbController<SiteAbout>{

}
