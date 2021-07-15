package com.solidarity.ejb.controller;

import javax.ejb.Remote;

import com.solidarity.model.Doacao;
import com.tedros.ejb.base.controller.ITEjbController;

@Remote
public interface IDoacaoController extends ITEjbController<Doacao>{
	
	
}
