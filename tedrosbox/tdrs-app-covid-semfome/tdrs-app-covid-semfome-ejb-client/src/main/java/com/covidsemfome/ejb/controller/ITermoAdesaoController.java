package com.covidsemfome.ejb.controller;

import javax.ejb.Remote;

import com.covidsemfome.model.TermoAdesao;
import com.tedros.ejb.base.controller.ITEjbController;

@Remote
public interface ITermoAdesaoController extends ITEjbController<TermoAdesao>{

}
