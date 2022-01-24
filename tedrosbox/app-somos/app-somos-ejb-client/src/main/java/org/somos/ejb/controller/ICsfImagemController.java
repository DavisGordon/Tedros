/**
 * 
 */
package org.somos.ejb.controller;

import javax.ejb.Remote;

import org.somos.model.CsfImagem;

import com.tedros.ejb.base.controller.ITSecureEjbController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface ICsfImagemController extends ITSecureEjbController<CsfImagem> {

}
