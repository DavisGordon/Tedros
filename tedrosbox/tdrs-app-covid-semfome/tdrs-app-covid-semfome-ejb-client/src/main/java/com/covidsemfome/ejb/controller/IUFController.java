package com.covidsemfome.ejb.controller;

import javax.ejb.Remote;

import com.covidsemfome.model.UF;
import com.tedros.ejb.base.controller.ITEjbController;

@Remote
public interface IUFController extends ITEjbController<UF>{

}
