package com.covidsemfome.ejb.service;

import javax.ejb.Remote;

import com.covidsemfome.model.TipoAjuda;
import com.tedros.ejb.base.service.ITEjbService;

@Remote
public interface ITipoAjudaService extends ITEjbService<TipoAjuda>{
	
}
