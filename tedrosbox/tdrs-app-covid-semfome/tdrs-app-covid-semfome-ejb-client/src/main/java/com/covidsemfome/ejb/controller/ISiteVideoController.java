package com.covidsemfome.ejb.controller;

import javax.ejb.Remote;

import com.covidsemfome.model.SiteVideo;
import com.tedros.ejb.base.controller.ITEjbController;

@Remote
public interface ISiteVideoController extends ITEjbController<SiteVideo>{

}
