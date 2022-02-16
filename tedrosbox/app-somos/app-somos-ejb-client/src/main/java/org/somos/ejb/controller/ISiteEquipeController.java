/**
 * 
 */
package org.somos.ejb.controller;

import javax.ejb.Remote;

import org.somos.model.SiteEquipe;

import com.tedros.ejb.base.controller.ITSecureEjbController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface ISiteEquipeController extends ITSecureEjbController<SiteEquipe> {

}
