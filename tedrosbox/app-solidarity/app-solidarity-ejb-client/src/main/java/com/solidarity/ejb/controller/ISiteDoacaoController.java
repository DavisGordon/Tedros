package com.solidarity.ejb.controller;

import javax.ejb.Remote;

import com.solidarity.model.SiteDoacao;
import com.tedros.ejb.base.controller.ITEjbController;

@Remote
public interface ISiteDoacaoController extends ITEjbController<SiteDoacao>{

}
