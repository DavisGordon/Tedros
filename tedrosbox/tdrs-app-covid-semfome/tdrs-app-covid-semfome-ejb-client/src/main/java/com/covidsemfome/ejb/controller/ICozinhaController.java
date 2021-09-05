package com.covidsemfome.ejb.controller;

import javax.ejb.Remote;

import com.covidsemfome.model.Cozinha;
import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface ICozinhaController extends ITSecureEjbController<Cozinha>{

}
