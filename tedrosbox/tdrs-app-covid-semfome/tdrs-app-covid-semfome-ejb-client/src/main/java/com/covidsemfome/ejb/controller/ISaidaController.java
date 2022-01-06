package com.covidsemfome.ejb.controller;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import com.covidsemfome.model.Cozinha;
import com.covidsemfome.model.Saida;
import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.security.TAccessToken;

@Remote
public interface ISaidaController extends ITSecureEjbController<Saida>{

	public TResult<List<Saida>> pesquisar(TAccessToken token, Cozinha coz, Date dataInicio, Date dataFim, String orderby, String ordertype, Long... idsl);
		
}
