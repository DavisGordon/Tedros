package org.somos.ejb.controller;

import javax.ejb.Remote;

import org.somos.model.SiteDoacao;

import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface ISiteDoacaoController extends ITSecureEjbController<SiteDoacao>{

}
