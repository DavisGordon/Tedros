package com.covidsemfome.ejb.controller;

import javax.ejb.Remote;

import com.covidsemfome.model.Saida;
import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface ISaidaController extends ITSecureEjbController<Saida>{

}
