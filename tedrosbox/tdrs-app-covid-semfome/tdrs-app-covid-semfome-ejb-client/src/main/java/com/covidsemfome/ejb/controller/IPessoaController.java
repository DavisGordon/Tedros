package com.covidsemfome.ejb.controller;

import java.util.Date;

import javax.ejb.Remote;

import com.covidsemfome.model.Pessoa;
import com.tedros.ejb.base.controller.ITEjbController;
import com.tedros.ejb.base.result.TResult;

@Remote
public interface IPessoaController extends ITEjbController<Pessoa>{
	
	@SuppressWarnings("rawtypes")
	public TResult pesquisar(String nome, Date dataNascimento, String tipo, String tipoDocumento, String numero);

	public TResult<Pessoa> saveFromSite(Pessoa entidade);
	
	public TResult recuperar(String loginName, String password);
	
	
}
