package org.somos.ejb.controller;

import javax.ejb.Remote;

import org.somos.model.SiteVideo;

import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface ISiteVideoController extends ITSecureEjbController<SiteVideo>{

}
