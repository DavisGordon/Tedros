package com.covidsemfome.ejb.service;

import java.util.Date;

import javax.ejb.Remote;

import com.covidsemfome.model.Pessoa;
import com.tedros.ejb.base.service.ITEjbService;
import com.tedros.ejb.base.service.TResult;

@Remote
public interface IPessoaService extends ITEjbService<Pessoa>{
	
	@SuppressWarnings("rawtypes")
	public TResult pesquisar(String nome, Date dataNascimento, String tipo, String tipoDocumento, String numero);

	public TResult<Pessoa> saveFromSite(Pessoa entidade);
	
	public TResult recuperar(String loginName, String password);
	
	
}
