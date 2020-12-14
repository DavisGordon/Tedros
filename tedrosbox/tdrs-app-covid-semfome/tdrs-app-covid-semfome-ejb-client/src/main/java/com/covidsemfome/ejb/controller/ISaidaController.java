package com.covidsemfome.ejb.controller;

import javax.ejb.Remote;

import com.covidsemfome.model.Saida;
import com.tedros.ejb.base.controller.ITEjbController;

@Remote
public interface ISaidaController extends ITEjbController<Saida>{

}
