package org.somos.ejb.controller;

import java.util.List;

import javax.ejb.Remote;

import org.somos.model.AjudaCampanha;
import org.somos.model.Campanha;
import org.somos.model.FormaAjuda;

import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.security.TAccessToken;

@Remote
public interface IAjudaCampanhaController extends ITSecureEjbController<AjudaCampanha>{
	
	TResult<List<AjudaCampanha>> recuperar(TAccessToken token, Campanha c, FormaAjuda fa, String p, Integer diasAtras);
		
}
