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

import org.somos.ejb.controller.ISiteParceriaController;
import org.somos.model.SiteParceria;
import org.somos.model.SiteParceria;
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
@Stateless(name="ISiteParceriaController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class SiteParceriaController extends TSecureEjbController<SiteParceria> implements ISiteParceriaController, ITSecurity {
	
	@EJB
	private TStatelessService<SiteParceria> serv;

	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<SiteParceria> getService() {
		return serv;
	}

	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}

	@Override
	public TResult<SiteParceria> save(TAccessToken token, SiteParceria e) {
		
		if(e.getStatus().equals("ATIVADO")) {
			SiteParceria ex = new SiteParceria();
			ex.setStatus("ATIVADO");
			try {
				List<SiteParceria> l = serv.findAll(ex);
				if(l!=null)
					for(SiteParceria i : l){
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
