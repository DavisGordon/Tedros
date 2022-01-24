/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package org.somos.server.estoque.controller;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.somos.ejb.controller.IEstoqueController;
import org.somos.model.Cozinha;
import org.somos.model.Estoque;
import org.somos.server.estoque.service.EstoqueService;

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
@Stateless(name="IEstoqueController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class EstoqueController extends TSecureEjbController<Estoque> implements IEstoqueController, ITSecurity {
	
	@EJB
	private EstoqueService serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<Estoque> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}
	
	public TResult<List<Estoque>> pesquisar(TAccessToken token, List<Long> idsl, Cozinha coz, Date dataInicio, Date dataFim, String origem, String orderby, String ordertype){
		try {
			List<Estoque> l = serv.pesquisar(idsl, coz, dataInicio, dataFim, origem, orderby, ordertype);
			return new TResult<>(EnumResult.SUCESS, l);
		} catch (Exception e) {
			return super.processException(token, null, e);
		}
	
	}
		

}
