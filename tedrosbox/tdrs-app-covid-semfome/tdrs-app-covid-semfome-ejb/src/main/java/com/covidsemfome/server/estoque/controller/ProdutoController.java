/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.server.estoque.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.covidsemfome.ejb.controller.IProdutoController;
import com.covidsemfome.model.Produto;
import com.covidsemfome.server.estoque.service.ProdutoService;
import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.controller.TSecureEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.ejb.base.security.ITSecurity;
import com.tedros.ejb.base.security.TAccessToken;
import com.tedros.ejb.base.security.TRemoteSecurity;
import com.tedros.ejb.base.service.ITEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@TRemoteSecurity
@Stateless(name="IProdutoController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ProdutoController extends TSecureEjbController<Produto> implements IProdutoController, ITSecurity {
	
	@EJB
	private ProdutoService serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<Produto> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}
	
	public TResult<List<Produto>> pesquisar(TAccessToken token, String cod, String nome, String marca, String medida, String uniMed, String orderby, String ordertype){
		try {
			List<Produto> l = serv.pesquisar(cod, nome, marca, medida, uniMed, orderby, ordertype);
			return new TResult<>(EnumResult.SUCESS, l);
		}catch(Exception e){
			return super.processException(token, null, e);
		}
	}
		

}
