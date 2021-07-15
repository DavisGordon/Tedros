package com.solidarity.ejb.controller;

import javax.ejb.Remote;

import com.solidarity.model.Cozinha;
import com.tedros.ejb.base.controller.ITEjbController;

@Remote
public interface ICozinhaController extends ITEjbController<Cozinha>{

}
