/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package org.somos.server.acao.service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.somos.model.TermoAdesao;
import org.somos.server.acao.bo.TermoAdesaoBO;

import com.tedros.ejb.base.service.TEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Local
@Stateless(name="TermoAdesaoService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TermoAdesaoService extends TEjbService<TermoAdesao> {
	
	@Inject
	private TermoAdesaoBO bo;
	
	@Override
	public TermoAdesaoBO getBussinesObject() {
		return bo;
	}
	

}
