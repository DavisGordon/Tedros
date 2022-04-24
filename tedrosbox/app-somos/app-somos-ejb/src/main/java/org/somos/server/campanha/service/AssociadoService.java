/**
 * 
 */
package org.somos.server.campanha.service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.somos.model.Associado;
import org.somos.model.Pessoa;
import org.somos.server.campanha.bo.AssociadoBO;

import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.service.TEjbService;

/**
 * @author Davis Gordon
 *
 */
@Local
@Stateless(name="AssociadoService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class AssociadoService extends TEjbService<Associado>  {

	@Inject
	private AssociadoBO bo;
	
	@Override
	public TGenericBO<Associado> getBussinesObject() {
		return bo;
	}
	
	public Associado recuperar(Pessoa p){
		return bo.recuperar(p);
	}
	
}
