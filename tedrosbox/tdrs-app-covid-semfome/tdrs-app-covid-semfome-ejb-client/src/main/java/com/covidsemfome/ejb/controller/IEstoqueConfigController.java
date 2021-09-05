package com.covidsemfome.ejb.controller;

import javax.ejb.Remote;

import com.covidsemfome.model.EstoqueConfig;
import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface IEstoqueConfigController extends ITSecureEjbController<EstoqueConfig>{

}
