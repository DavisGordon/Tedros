/**
 * 
 */
package com.covidsemfome.ejb.service;

import javax.ejb.Remote;

import com.covidsemfome.model.Mailing;
import com.tedros.ejb.base.service.ITEjbService;

/**
 * @author Davis Gordon
 *
 */

@Remote
public interface IMailingService extends ITEjbService<Mailing> {

}
