/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package org.somos.server.acao.service;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.somos.model.TipoAjuda;
import org.somos.server.acao.bo.TipoAjudaBO;

import com.tedros.ejb.base.service.TEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Local
@Stateless(name="ITipoAjudaService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TipoAjudaService extends TEjbService<TipoAjuda> {
	
	@Inject
	private TipoAjudaBO bo;
	
	@Override
	public TipoAjudaBO getBussinesObject() {
		return bo;
	}
	
	public List<TipoAjuda> listar(String tipo){
		return bo.listar(tipo);
	}

}
