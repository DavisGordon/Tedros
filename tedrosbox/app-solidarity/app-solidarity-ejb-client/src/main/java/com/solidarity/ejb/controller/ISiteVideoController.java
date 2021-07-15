package com.solidarity.ejb.controller;

import javax.ejb.Remote;

import com.solidarity.model.SiteVideo;
import com.tedros.ejb.base.controller.ITEjbController;

@Remote
public interface ISiteVideoController extends ITEjbController<SiteVideo>{

}
