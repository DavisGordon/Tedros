package com.covidsemfome.ejb.service;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;

import com.covidsemfome.model.TipoAjuda;
import com.tedros.ejb.base.service.ITEjbService;
import com.tedros.ejb.base.service.TResult;

@Local
@Remote
public interface ITipoAjudaService extends ITEjbService<TipoAjuda>{
	public TResult<List<TipoAjuda>> listar(String tipo);
}
