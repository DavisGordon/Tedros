package org.somos.ejb.controller;

import javax.ejb.Remote;

import org.somos.parceiro.model.SiteConteudo;

import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface ISiteConteudoController extends ITSecureEjbController<SiteConteudo>{

}
