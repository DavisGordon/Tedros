package com.solidarity.ejb.controller;

import javax.ejb.Remote;

import com.solidarity.model.TermoAdesao;
import com.tedros.ejb.base.controller.ITEjbController;

@Remote
public interface ITermoAdesaoController extends ITEjbController<TermoAdesao>{

}
