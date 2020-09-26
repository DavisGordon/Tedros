package com.covidsemfome.ejb.controller;

import javax.ejb.Remote;

import com.covidsemfome.model.Producao;
import com.tedros.ejb.base.controller.ITEjbController;

@Remote
public interface IProducaoController extends ITEjbController<Producao>{

}
