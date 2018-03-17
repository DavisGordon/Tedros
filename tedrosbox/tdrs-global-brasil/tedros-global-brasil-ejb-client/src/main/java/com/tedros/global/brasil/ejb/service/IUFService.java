package com.tedros.global.brasil.ejb.service;

import javax.ejb.Local;
import javax.ejb.Remote;

import com.tedros.ejb.base.service.ITEjbService;
import com.tedros.global.brasil.model.UF;

@Remote
@Local
public interface IUFService extends ITEjbService<UF>{
		
}
