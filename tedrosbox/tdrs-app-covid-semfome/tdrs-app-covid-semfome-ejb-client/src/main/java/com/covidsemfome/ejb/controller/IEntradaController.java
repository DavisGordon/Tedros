package com.covidsemfome.ejb.controller;

import javax.ejb.Remote;

import com.covidsemfome.model.Entrada;
import com.tedros.ejb.base.controller.ITEjbController;

@Remote
public interface IEntradaController extends ITEjbController<Entrada>{

}
