package com.covidsemfome.ejb.controller;

import javax.ejb.Remote;

import com.covidsemfome.model.SiteNoticia;
import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface ISiteNoticiaController extends ITSecureEjbController<SiteNoticia>{

}
