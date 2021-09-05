package com.covidsemfome.ejb.controller;

import javax.ejb.Remote;

import com.covidsemfome.model.UF;
import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface IUFController extends ITSecureEjbController<UF>{

}
