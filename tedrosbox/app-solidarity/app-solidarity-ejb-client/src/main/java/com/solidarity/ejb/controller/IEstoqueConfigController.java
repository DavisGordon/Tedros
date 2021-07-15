package com.solidarity.ejb.controller;

import javax.ejb.Remote;

import com.solidarity.model.EstoqueConfig;
import com.tedros.ejb.base.controller.ITEjbController;

@Remote
public interface IEstoqueConfigController extends ITEjbController<EstoqueConfig>{

}
