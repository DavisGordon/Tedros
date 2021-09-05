/**
 * 
 */
package com.covidsemfome.ejb.controller;

import javax.ejb.Remote;

import com.covidsemfome.model.Mailing;
import com.tedros.ejb.base.controller.ITSecureEjbController;

/**
 * @author Davis Gordon
 *
 */

@Remote
public interface IMailingController extends ITSecureEjbController<Mailing> {

}
