package org.somos.ejb.controller;

import javax.ejb.Remote;

import org.somos.model.Cozinha;

import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface ICozinhaController extends ITSecureEjbController<Cozinha>{

}
