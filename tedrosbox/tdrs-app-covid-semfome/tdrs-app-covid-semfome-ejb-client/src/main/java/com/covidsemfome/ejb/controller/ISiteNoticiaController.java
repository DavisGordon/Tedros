package com.covidsemfome.ejb.controller;

import javax.ejb.Remote;

import com.covidsemfome.model.SiteNoticia;
import com.tedros.ejb.base.controller.ITEjbController;

@Remote
public interface ISiteNoticiaController extends ITEjbController<SiteNoticia>{

}
