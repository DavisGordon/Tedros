/**
 * 
 */
package com.covidsemfome.ejb.controller;

import javax.ejb.Remote;

import com.covidsemfome.parceiro.model.ComponentTemplate;
import com.tedros.ejb.base.controller.ITSecureEjbController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IComponentTemplateController extends ITSecureEjbController<ComponentTemplate> {

}
