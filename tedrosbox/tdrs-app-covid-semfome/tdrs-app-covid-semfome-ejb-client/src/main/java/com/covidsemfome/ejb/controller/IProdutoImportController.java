/**
 * 
 */
package com.covidsemfome.ejb.controller;

import javax.ejb.Remote;

import com.covidsemfome.model.Produto;
import com.tedros.ejb.base.controller.ITEjbImportController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IProdutoImportController extends ITEjbImportController<Produto> {

}
