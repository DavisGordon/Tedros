/**
 * 
 */
package com.covidsemfome.ejb.controller;

import javax.ejb.Remote;

import com.covidsemfome.parceiro.model.CssClass;
import com.tedros.ejb.base.controller.ITEjbImportController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface ICssClassImportController extends ITEjbImportController<CssClass> {

}
