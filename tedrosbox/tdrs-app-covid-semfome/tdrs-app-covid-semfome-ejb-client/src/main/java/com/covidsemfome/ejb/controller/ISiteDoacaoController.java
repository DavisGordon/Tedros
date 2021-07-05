package com.covidsemfome.ejb.controller;

import javax.ejb.Remote;

import com.covidsemfome.model.SiteDoacao;
import com.tedros.ejb.base.controller.ITEjbController;

@Remote
public interface ISiteDoacaoController extends ITEjbController<SiteDoacao>{

}
