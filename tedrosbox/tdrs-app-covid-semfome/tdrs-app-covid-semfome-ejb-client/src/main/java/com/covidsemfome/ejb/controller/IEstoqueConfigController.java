package com.covidsemfome.ejb.controller;

import javax.ejb.Remote;

import com.covidsemfome.model.EstoqueConfig;
import com.tedros.ejb.base.controller.ITEjbController;

@Remote
public interface IEstoqueConfigController extends ITEjbController<EstoqueConfig>{

}
