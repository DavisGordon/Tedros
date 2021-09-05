package com.covidsemfome.ejb.controller;

import javax.ejb.Remote;

import com.covidsemfome.model.TermoAdesao;
import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface ITermoAdesaoController extends ITSecureEjbController<TermoAdesao>{

}
