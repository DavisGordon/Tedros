package com.covidsemfome.ejb.controller;

import java.util.Date;

import javax.ejb.Remote;

import com.covidsemfome.model.Pessoa;
import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.security.TAccessToken;

@Remote
public interface IPessoaController extends ITSecureEjbController<Pessoa>{
	
	@SuppressWarnings("rawtypes")
	public TResult pesquisar(TAccessToken token, String nome, Date dataNascimento, String tipo, String tipoDocumento, String numero);

	public TResult<Pessoa> saveFromSite(TAccessToken token, Pessoa entidade);
	
	public TResult recuperar(TAccessToken token, String loginName, String password);
	
	public TResult<Boolean> newPass(TAccessToken token, String email);
	
	public TResult<Boolean> validateNewPassKey(TAccessToken token, String key);

	public TResult<Boolean> defpass(TAccessToken token, String email, String pass);
	
	public TResult<Boolean> isLoginInUse(TAccessToken token, String email);
}
