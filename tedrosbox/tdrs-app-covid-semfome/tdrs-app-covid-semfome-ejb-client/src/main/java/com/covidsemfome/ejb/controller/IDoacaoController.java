package com.covidsemfome.ejb.controller;

import java.util.Date;

import javax.ejb.Remote;

import com.covidsemfome.model.Doacao;
import com.covidsemfome.model.TipoAjuda;
import com.tedros.ejb.base.controller.ITEjbController;
import com.tedros.ejb.base.result.TResult;

@Remote
public interface IDoacaoController extends ITEjbController<Doacao>{
	
	@SuppressWarnings("rawtypes")
	public TResult pesquisar(String nome, Date dataInicio, Date dataFim, Long acaoId, TipoAjuda tipoAjuda);

	
}
