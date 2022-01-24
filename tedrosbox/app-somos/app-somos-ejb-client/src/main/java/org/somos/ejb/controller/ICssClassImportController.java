/**
 * 
 */
package org.somos.ejb.controller;

import javax.ejb.Remote;

import org.somos.parceiro.model.CssClass;

import com.tedros.ejb.base.controller.ITEjbImportController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface ICssClassImportController extends ITEjbImportController<CssClass> {

}
