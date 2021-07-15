package com.solidarity.ejb.controller;

import javax.ejb.Remote;

import com.solidarity.model.Entrada;
import com.tedros.ejb.base.controller.ITEjbController;

@Remote
public interface IEntradaController extends ITEjbController<Entrada>{

}
