/**
 * 
 */
package org.somos.ejb.controller;

import javax.ejb.Remote;

import org.somos.model.Mailing;

import com.tedros.ejb.base.controller.ITSecureEjbController;

/**
 * @author Davis Gordon
 *
 */

@Remote
public interface IMailingController extends ITSecureEjbController<Mailing> {

}
