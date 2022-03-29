/**
 * 
 */
package com.template.ejb.controller;

import javax.ejb.Remote;

import com.tedros.ejb.base.controller.ITEjbImportController;
import com.template.model.Produto;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IProdutoImportController extends ITEjbImportController<Produto> {

}
