package com.covidsemfome.ejb.controller;

import javax.ejb.Remote;

import com.covidsemfome.model.Doacao;
import com.tedros.ejb.base.controller.ITEjbController;

@Remote
public interface IDoacaoController extends ITEjbController<Doacao>{
	
	
}
