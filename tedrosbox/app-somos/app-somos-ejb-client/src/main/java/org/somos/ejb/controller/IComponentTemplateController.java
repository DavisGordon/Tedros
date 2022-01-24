/**
 * 
 */
package org.somos.ejb.controller;

import javax.ejb.Remote;

import org.somos.parceiro.model.ComponentTemplate;

import com.tedros.ejb.base.controller.ITSecureEjbController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IComponentTemplateController extends ITSecureEjbController<ComponentTemplate> {

}
