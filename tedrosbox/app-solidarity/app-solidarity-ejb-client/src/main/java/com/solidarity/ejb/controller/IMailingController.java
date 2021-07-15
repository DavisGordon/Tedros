/**
 * 
 */
package com.solidarity.ejb.controller;

import javax.ejb.Remote;

import com.solidarity.model.Mailing;
import com.tedros.ejb.base.controller.ITEjbController;

/**
 * @author Davis Gordon
 *
 */

@Remote
public interface IMailingController extends ITEjbController<Mailing> {

}
