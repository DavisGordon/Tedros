package com.solidarity.ejb.controller;

import javax.ejb.Remote;

import com.solidarity.model.Produto;
import com.tedros.ejb.base.controller.ITEjbController;

@Remote
public interface IProdutoController extends ITEjbController<Produto>{

}
