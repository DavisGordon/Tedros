package org.somos.ejb.controller;

import javax.ejb.Remote;

import org.somos.model.UF;

import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface IUFController extends ITSecureEjbController<UF>{

}
