package com.covidsemfome.ejb.controller;

import javax.ejb.Remote;

import com.covidsemfome.model.Produto;
import com.tedros.ejb.base.controller.ITEjbController;

@Remote
public interface IProdutoController extends ITEjbController<Produto>{

}
