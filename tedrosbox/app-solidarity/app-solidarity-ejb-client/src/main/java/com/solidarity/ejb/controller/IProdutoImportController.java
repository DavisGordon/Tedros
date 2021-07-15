/**
 * 
 */
package com.solidarity.ejb.controller;

import javax.ejb.Remote;

import com.solidarity.model.Produto;
import com.tedros.ejb.base.controller.ITEjbImportController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IProdutoImportController extends ITEjbImportController<Produto> {

}
