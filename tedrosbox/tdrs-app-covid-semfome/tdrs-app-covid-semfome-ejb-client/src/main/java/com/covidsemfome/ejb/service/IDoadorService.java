package com.covidsemfome.ejb.service;

import java.util.Date;

import javax.ejb.Remote;

import com.covidsemfome.model.Doador;
import com.tedros.ejb.base.service.ITEjbService;
import com.tedros.ejb.base.service.TResult;

@Remote
public interface IDoadorService extends ITEjbService<Doador>{
	
	@SuppressWarnings("rawtypes")
	public TResult pesquisar(String nome, Date dataNascimento);

	
}
