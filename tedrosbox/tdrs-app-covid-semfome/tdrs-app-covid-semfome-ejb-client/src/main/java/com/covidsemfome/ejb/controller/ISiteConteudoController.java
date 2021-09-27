package com.covidsemfome.ejb.controller;

import javax.ejb.Remote;

import com.covidsemfome.parceiro.model.SiteConteudo;
import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface ISiteConteudoController extends ITSecureEjbController<SiteConteudo>{

}
