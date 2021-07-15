package com.solidarity.ejb.controller;

import javax.ejb.Remote;

import com.solidarity.model.Estoque;
import com.tedros.ejb.base.controller.ITEjbController;

@Remote
public interface IEstoqueController extends ITEjbController<Estoque>{

}
