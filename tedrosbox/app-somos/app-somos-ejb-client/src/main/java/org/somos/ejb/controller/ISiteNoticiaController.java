package org.somos.ejb.controller;

import javax.ejb.Remote;

import org.somos.model.SiteNoticia;

import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface ISiteNoticiaController extends ITSecureEjbController<SiteNoticia>{

}
