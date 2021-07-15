package com.solidarity.ejb.controller;

import javax.ejb.Remote;

import com.solidarity.model.UF;
import com.tedros.ejb.base.controller.ITEjbController;

@Remote
public interface IUFController extends ITEjbController<UF>{

}
