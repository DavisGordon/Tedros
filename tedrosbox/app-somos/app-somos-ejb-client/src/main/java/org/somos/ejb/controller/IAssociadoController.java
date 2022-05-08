package org.somos.ejb.controller;

import javax.ejb.Remote;

import org.somos.model.Associado;
import org.somos.model.DetalheAjuda;
import org.somos.model.Pessoa;

import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.security.TAccessToken;

@Remote
public interface IAssociadoController extends ITSecureEjbController<Associado>{
	 
	TResult<Associado> recuperar(TAccessToken token, Pessoa p);
	
	TResult<Associado> ajudarCampanha(TAccessToken token, Pessoa p, 
			Long idCamp, String valor, String periodo, Long idForma, DetalheAjuda detAjuda);
	
	 TResult<Associado> cancelarAjuda(TAccessToken token, Pessoa p, Long idCamp);
	
}
