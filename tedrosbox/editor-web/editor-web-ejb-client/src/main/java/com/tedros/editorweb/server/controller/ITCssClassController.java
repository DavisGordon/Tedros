/**
 * 
 */
package com.tedros.editorweb.server.controller;

import javax.ejb.Remote;

import com.tedros.editorweb.model.CssClass;
import com.tedros.ejb.base.controller.ITSecureEjbController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface ITCssClassController extends ITSecureEjbController<CssClass> {

}
