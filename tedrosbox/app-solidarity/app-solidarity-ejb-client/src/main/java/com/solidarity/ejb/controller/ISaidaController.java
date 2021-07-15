package com.solidarity.ejb.controller;

import javax.ejb.Remote;

import com.solidarity.model.Saida;
import com.tedros.ejb.base.controller.ITEjbController;

@Remote
public interface ISaidaController extends ITEjbController<Saida>{

}
