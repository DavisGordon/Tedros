package com.covidsemfome.ejb.controller;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import com.covidsemfome.model.Cozinha;
import com.covidsemfome.model.Estoque;
import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.security.TAccessToken;

@Remote
public interface IEstoqueController extends ITSecureEjbController<Estoque>{
	public TResult<List<Estoque>> pesquisar(TAccessToken token, List<Long> idsl, Cozinha coz, Date dataInicio, Date dataFim, 
			String origem, String orderby, String ordertype);
}
