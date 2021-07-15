package com.solidarity.ejb.controller;

import javax.ejb.Remote;

import com.solidarity.model.SiteNoticia;
import com.tedros.ejb.base.controller.ITEjbController;

@Remote
public interface ISiteNoticiaController extends ITEjbController<SiteNoticia>{

}
