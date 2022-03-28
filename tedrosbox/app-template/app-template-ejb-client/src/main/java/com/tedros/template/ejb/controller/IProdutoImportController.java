/**
 * 
 */
package com.tedros.template.ejb.controller;

import javax.ejb.Remote;

import com.tedros.ejb.base.controller.ITEjbImportController;
import com.tedros.template.model.Produto;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IProdutoImportController extends ITEjbImportController<Produto> {

}
