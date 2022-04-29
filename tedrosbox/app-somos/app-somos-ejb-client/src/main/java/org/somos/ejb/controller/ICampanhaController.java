package org.somos.ejb.controller;

import java.util.List;

import javax.ejb.Remote;

import org.somos.model.Campanha;

import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.security.TAccessToken;

@Remote
public interface ICampanhaController extends ITSecureEjbController<Campanha>{
	
	TResult<List<Campanha>> listarValidos(TAccessToken token);
}
