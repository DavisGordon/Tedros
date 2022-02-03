package org.somos.ejb.controller;

import javax.ejb.Remote;

import org.somos.model.SiteDoacao;
import org.somos.model.SiteSMDoacao;

import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface ISiteSMDoacaoController extends ITSecureEjbController<SiteSMDoacao>{

}
