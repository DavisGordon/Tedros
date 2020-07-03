package com.covidsemfome.ejb.service;

import javax.ejb.Local;
import javax.ejb.Remote;

import com.covidsemfome.model.Voluntario;
import com.tedros.ejb.base.service.ITEjbService;

@Remote
@Local
public interface IVoluntarioService extends ITEjbService<Voluntario>{
	

}
