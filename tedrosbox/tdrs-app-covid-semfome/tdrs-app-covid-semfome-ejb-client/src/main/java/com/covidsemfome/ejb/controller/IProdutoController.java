package com.covidsemfome.ejb.controller;

import javax.ejb.Remote;

import com.covidsemfome.model.Produto;
import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface IProdutoController extends ITSecureEjbController<Produto>{

}
