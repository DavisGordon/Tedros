package org.somos.ejb.controller;

import javax.ejb.Remote;

import org.somos.model.AjudaCampanha;

import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface IAjudaCampanhaController extends ITSecureEjbController<AjudaCampanha>{
		
}
