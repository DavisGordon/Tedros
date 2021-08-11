/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.ejb.service;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.covidsemfome.ejb.bo.ProdutoBO;
import com.covidsemfome.model.Produto;
import com.tedros.ejb.base.service.TEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Local
@Stateless(name="ProdutoService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ProdutoService extends TEjbService<Produto> {
	
	@Inject
	private ProdutoBO bo;
	
	@Override
	public ProdutoBO getBussinesObject() {
		return bo;
	}
	
	public List<Produto> pesquisar(String cod, String nome, String marca, String medida, String uniMed){
		return bo.pesquisar(cod, nome, marca, medida, uniMed);
	}

}
