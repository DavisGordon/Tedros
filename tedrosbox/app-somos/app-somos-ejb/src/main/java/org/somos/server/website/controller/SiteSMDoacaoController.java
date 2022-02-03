/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package org.somos.server.website.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.somos.ejb.controller.ISiteSMDoacaoController;
import org.somos.model.SiteAbout;
import org.somos.model.SiteSMDoacao;
import org.somos.server.base.service.TStatelessService;

import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.controller.TSecureEjbController;
import com.tedros.ejb.base.result.TResult;
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
@Stateless(name="ISiteSMDoacaoController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class SiteSMDoacaoController extends TSecureEjbController<SiteSMDoacao> implements ISiteSMDoacaoController, ITSecurity {
	
	@EJB
	private TStatelessService<SiteSMDoacao> serv;

	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<SiteSMDoacao> getService() {
		return serv;
	}

	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}

	@Override
	public TResult<SiteSMDoacao> save(TAccessToken token, SiteSMDoacao e) {
		
		if(e.getPontosColeta()!=null)
			e.getPontosColeta().forEach(i ->{
				i.setDoacao(e);
			});
		
		if(e.getStatus().equals("ATIVADO")) {
			SiteSMDoacao ex = new SiteSMDoacao();
			ex.setStatus("ATIVADO");
			try {
				List<SiteSMDoacao> l = serv.findAll(ex);
				if(l!=null)
					for(SiteSMDoacao i : l){
						if(!e.isNew() && i.getId().equals(e.getId())) 
							continue;
						i.setStatus("DESATIVADO");
						serv.save(i);
					}
			} catch (Exception e1) {
				return processException(token, e, e1);
			}
		}
		
		return super.save(token, e);
	}
}
