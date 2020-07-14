/**
 * 
 */
package com.covidsemfome.ejb.controller;

import javax.ejb.Remote;

import com.covidsemfome.model.Mailing;
import com.tedros.ejb.base.controller.ITEjbController;

/**
 * @author Davis Gordon
 *
 */

@Remote
public interface IMailingController extends ITEjbController<Mailing> {

}
