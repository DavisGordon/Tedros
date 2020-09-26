package com.covidsemfome.ejb.controller;

import javax.ejb.Remote;

import com.covidsemfome.model.Cozinha;
import com.tedros.ejb.base.controller.ITEjbController;

@Remote
public interface ICozinhaController extends ITEjbController<Cozinha>{

}
