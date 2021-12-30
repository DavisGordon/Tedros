package com.covidsemfome.ejb.controller;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import com.covidsemfome.model.Cozinha;
import com.covidsemfome.model.Entrada;
import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.security.TAccessToken;

@Remote
public interface IEntradaController extends ITSecureEjbController<Entrada>{

	public TResult<List<Entrada>> pesquisar(TAccessToken token, Cozinha coz, Date dataInicio, Date dataFim, 
			String tipo, String orderby, String ordertype, Long... idsl);
		
}
