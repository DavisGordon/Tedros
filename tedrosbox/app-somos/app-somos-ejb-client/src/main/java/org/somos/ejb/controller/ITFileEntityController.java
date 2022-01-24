package org.somos.ejb.controller;

import javax.ejb.Remote;

import com.tedros.common.model.TFileEntity;
import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface ITFileEntityController extends ITSecureEjbController<TFileEntity>{

}
