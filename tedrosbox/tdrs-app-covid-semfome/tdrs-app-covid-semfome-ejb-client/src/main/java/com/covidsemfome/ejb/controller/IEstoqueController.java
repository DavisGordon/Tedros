package com.covidsemfome.ejb.controller;

import javax.ejb.Remote;

import com.covidsemfome.model.Estoque;
import com.tedros.ejb.base.controller.ITEjbController;

@Remote
public interface IEstoqueController extends ITEjbController<Estoque>{

}
