package org.somos.ejb.controller;

import java.util.List;

import javax.ejb.Remote;

import org.somos.model.Cozinha;
import org.somos.model.EstoqueConfig;

import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.security.TAccessToken;

@Remote
public interface IEstoqueConfigController extends ITSecureEjbController<EstoqueConfig>{


	public TResult<List<EstoqueConfig>> pesquisar(TAccessToken token, Cozinha cozinha);
}
