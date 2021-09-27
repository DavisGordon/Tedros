package com.covidsemfome.ejb.controller;

import javax.ejb.Remote;

import com.covidsemfome.parceiro.model.EmpresaParceira;
import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface IEmpresaParceiraController extends ITSecureEjbController<EmpresaParceira>{

}
