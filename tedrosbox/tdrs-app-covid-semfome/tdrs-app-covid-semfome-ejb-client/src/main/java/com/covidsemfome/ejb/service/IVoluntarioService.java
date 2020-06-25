package com.covidsemfome.ejb.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import com.covidsemfome.model.Acao;
import com.covidsemfome.model.Pessoa;
import com.tedros.ejb.base.service.ITEjbService;
import com.tedros.ejb.base.service.TResult;

@Remote
public interface IVoluntarioService extends ITEjbService<Pessoa>{
	
	@SuppressWarnings("rawtypes")
	public TResult pesquisar(String nome, Date dataNascimento, String tipo, String tipoDocumento, String numero);

	public TResult<Pessoa> saveFromSite(Pessoa entidade);
	
	public TResult recuperar(String loginName, String password);
	
	public TResult<List<Acao>> participarEmAcao(Pessoa pessoa, Long acaoId);
	
	public TResult<List<Acao>> sairDaAcao(Pessoa pessoa, Long acaoId);
}
