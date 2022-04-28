/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package org.somos.server.campanha.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.somos.ejb.controller.ICampanhaMailConfigController;
import org.somos.model.AjudaCampanha;
import org.somos.model.CampanhaMailConfig;
import org.somos.server.acao.bo.EmailBO;
import org.somos.server.campanha.service.AjudaCampanhaService;
import org.somos.server.campanha.service.CampanhaMailConfigService;
import org.somos.server.exception.EmailBusinessException;

import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.controller.TSecureEjbController;
import com.tedros.ejb.base.security.ITSecurity;
import com.tedros.ejb.base.security.TRemoteSecurity;
import com.tedros.ejb.base.service.ITEjbService;
import com.tedros.util.TSentEmailException;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 **/
@TRemoteSecurity
@Stateless(name="ICampanhaMailConfigController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class CampanhaMailConfigController extends TSecureEjbController<CampanhaMailConfig> implements ICampanhaMailConfigController, ITSecurity {
	
	@EJB
	private CampanhaMailConfigService serv;
	
	@EJB
	private AjudaCampanhaService acServ;
	
	@EJB
	private ITSecurityController securityController;
	
	@Inject
	private EmailBO emailBo;

	@Override
	public ITEjbService<CampanhaMailConfig> getService() {
		return serv;
	}

	/**
	 * @return the securityController
	 */
	public ITSecurityController getSecurityController() {
		return securityController;
	}

	public void processarMailing() {
		try {
			CampanhaMailConfig ex = new CampanhaMailConfig();
			ex.setStatus("ATIVADO");
			List<CampanhaMailConfig> lst = serv.findAll(ex);
			List<AjudaCampanha> enviados = new ArrayList<>();
			if(lst!=null && !lst.isEmpty()) {
				for(CampanhaMailConfig cmc : lst) {
					List<AjudaCampanha> novosLst = acServ.naoProcessados(cmc.getCampanha(), cmc.getFormaAjuda());
					enviarMailing(enviados, cmc, novosLst);
					List<AjudaCampanha> perLst = acServ.processarNoPeriodo(cmc.getCampanha(), cmc.getFormaAjuda());
					enviarMailing(enviados, cmc, perLst);
				}
				
				if(!enviados.isEmpty()) {
					enviados.forEach(ac->{
						try {
							ac.setDataProcessado(new Date());
							acServ.save(ac);
						} catch (Exception e) {
							e.printStackTrace();
						}
					});
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void enviarMailing(List<AjudaCampanha> enviados, CampanhaMailConfig cmc, List<AjudaCampanha> novosLst) {
		novosLst.parallelStream().forEach(ac->{
			String content = serv.prepareContent(cmc, ac);
			try {
				emailBo.enviarMailing(false, ac.getAssociado().getPessoa().getEmail(), cmc.getTitulo(), content);
				enviados.add(ac);
			} catch (TSentEmailException | EmailBusinessException e) {
				e.printStackTrace();
			}
			
		});
	}
}
