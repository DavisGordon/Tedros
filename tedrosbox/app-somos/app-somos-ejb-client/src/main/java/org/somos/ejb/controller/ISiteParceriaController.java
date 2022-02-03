package org.somos.ejb.controller;

import javax.ejb.Remote;

import org.somos.model.SiteParceria;

import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface ISiteParceriaController extends ITSecureEjbController<SiteParceria>{

}
