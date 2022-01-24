/**
 * 
 */
package org.somos.ejb.controller;

import javax.ejb.Remote;

import org.somos.parceiro.model.CssClass;

import com.tedros.ejb.base.controller.ITSecureEjbController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface ICssClassController extends ITSecureEjbController<CssClass> {

}
