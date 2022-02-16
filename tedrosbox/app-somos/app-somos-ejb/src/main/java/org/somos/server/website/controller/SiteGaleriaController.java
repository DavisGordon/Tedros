/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package org.somos.server.website.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.somos.ejb.controller.ISiteGaleriaController;
import org.somos.model.SiteGaleria;
import org.somos.server.base.service.TStatelessService;

import com.tedros.common.model.TFileEntity;
import com.tedros.core.ejb.controller.TFileEntityController;
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
@Stateless(name="ISiteGaleriaController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class SiteGaleriaController extends TSecureEjbController<SiteGaleria> implements ISiteGaleriaController, ITSecurity {
	
	@EJB
	private TStatelessService<SiteGaleria> serv;
	
	@EJB
	private TFileEntityController fileServ; 
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<SiteGaleria> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}

	@Override
	public TResult<SiteGaleria> save(TAccessToken token, SiteGaleria e) {
		
		List<TFileEntity> filesToRem = new ArrayList<>();
		if(!e.isNew()) {
			try {
				SiteGaleria  x = serv.findById(e);
				if((x.getImage()!=null && e.getImage()==null) ||
						(x.getImage()!=null && e.getImage()!=null && !x.getImage().getId().equals(e.getImage().getId())))
					filesToRem.add(x.getImage());
			} catch (Exception e1) {
			}
		}
		
		TResult<SiteGaleria> res = super.save(token, e);
		
		if(!filesToRem.isEmpty()) {
			for(TFileEntity f : filesToRem)
				fileServ.remove(token, f);
		}
		
		return res;
	}
}
