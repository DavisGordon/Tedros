/**
 * 
 */
package com.tedros.editorweb.server.controller;

import javax.ejb.Remote;

import com.tedros.editorweb.model.Parameter;
import com.tedros.ejb.base.controller.ITSecureEjbController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface ITParameterController extends ITSecureEjbController<Parameter> {

}
