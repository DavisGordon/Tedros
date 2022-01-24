/**
 * 
 */
package org.somos.ejb.controller;

import javax.ejb.Remote;

import org.somos.model.Produto;

import com.tedros.ejb.base.controller.ITEjbImportController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IProdutoImportController extends ITEjbImportController<Produto> {

}
