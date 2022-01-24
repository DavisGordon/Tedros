package org.somos.ejb.controller;

import javax.ejb.Remote;

import org.somos.parceiro.model.EmpresaParceira;

import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface IEmpresaParceiraController extends ITSecureEjbController<EmpresaParceira>{

}
