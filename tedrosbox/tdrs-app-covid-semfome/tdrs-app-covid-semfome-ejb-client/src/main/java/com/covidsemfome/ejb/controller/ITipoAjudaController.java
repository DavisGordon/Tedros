package com.covidsemfome.ejb.controller;

import java.util.List;

import javax.ejb.Remote;

import com.covidsemfome.model.TipoAjuda;
import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.ejb.base.result.TResult;

@Remote
public interface ITipoAjudaController extends ITSecureEjbController<TipoAjuda>{
	public TResult<List<TipoAjuda>> listar(String tipo);
}
