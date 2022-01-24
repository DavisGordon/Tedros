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

import org.somos.model.Voluntario;
import org.somos.report.model.VoluntarioReportModel;
import org.somos.server.acao.bo.VoluntarioBO;

import com.tedros.ejb.base.service.TEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Local
@Stateless(name="IVoluntarioService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class VoluntarioService extends TEjbService<Voluntario>  {
	
	@Inject
	private VoluntarioBO bo;
	
	@Override
	public VoluntarioBO getBussinesObject() {
		return bo;
	}
	
	@TransactionAttribute(value = TransactionAttributeType.NEVER)
	public VoluntarioReportModel pesquisar(VoluntarioReportModel m){
		return bo.pesquisar(m);
	}
	
}
