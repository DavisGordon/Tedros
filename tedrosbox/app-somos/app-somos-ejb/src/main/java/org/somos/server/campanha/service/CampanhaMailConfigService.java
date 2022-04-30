/**
 * 
 */
package org.somos.server.campanha.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.somos.model.AjudaCampanha;
import org.somos.model.CampanhaMailConfig;
import org.somos.server.campanha.bo.CampanhaMailConfigBO;

import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.service.TEjbService;

/**
 * @author Davis Gordon
 *
 */
@Local
@Stateless(name="CampanhaMailConfigService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class CampanhaMailConfigService extends TEjbService<CampanhaMailConfig>  {

	@Inject
	private CampanhaMailConfigBO bo;
	

	@EJB
	private AjudaCampanhaService acServ;
	
	@Override
	public TGenericBO<CampanhaMailConfig> getBussinesObject() {
		return bo;
	}
	
	public void processarMailing() {
		try {
			List<AjudaCampanha> enviados = bo.processarMailing();
			if(!enviados.isEmpty()) {
				enviados.forEach(ac->{
					try {
						acServ.setProcessado(ac);
						acServ.save(ac);
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	
}
