package com.tedros.global.brasil.ejb.service;

import java.util.Date;

import javax.ejb.Local;
import javax.ejb.Remote;

import com.tedros.ejb.base.service.ITEjbService;
import com.tedros.ejb.base.service.TResult;
import com.tedros.global.brasil.model.Pessoa;

@Remote
@Local
public interface IPessoaService extends ITEjbService<Pessoa>{
	
	@SuppressWarnings("rawtypes")
	public TResult pesquisar(String nome, Date dataNascimento, String tipo, String tipoDocumento, String numero);

	
}
