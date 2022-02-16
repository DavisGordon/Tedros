/**
 * 
 */
package org.somos.ejb.controller;

import javax.ejb.Remote;

import org.somos.model.SiteGaleria;

import com.tedros.ejb.base.controller.ITSecureEjbController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface ISiteGaleriaController extends ITSecureEjbController<SiteGaleria> {

}
