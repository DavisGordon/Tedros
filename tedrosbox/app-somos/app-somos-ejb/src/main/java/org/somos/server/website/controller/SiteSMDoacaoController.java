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

import org.somos.ejb.controller.ISiteSMDoacaoController;
import org.somos.model.SiteSMDoacao;
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
@Stateless(name="ISiteSMDoacaoController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class SiteSMDoacaoController extends TSecureEjbController<SiteSMDoacao> implements ISiteSMDoacaoController, ITSecurity {
	
	@EJB
	private TStatelessService<SiteSMDoacao> serv;

	@EJB
	private ITSecurityController securityController;
	
	@EJB
	private TFileEntityController fileServ; 
	
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
		List<TFileEntity> filesToRem = new ArrayList<>();
		if(!e.isNew()) {
			try {
				SiteSMDoacao  x = serv.findById(e);
				if((x.getImage()!=null && e.getImage()==null) ||
						(x.getImage()!=null && e.getImage()!=null && !x.getImage().getId().equals(e.getImage().getId())))
					filesToRem.add(x.getImage());
				
				if((x.getTransferencia()!=null && e.getTransferencia()==null)
						|| (x.getTransferencia()!=null && e.getTransferencia()!=null && x.getTransferencia().getImage()!=null && e.getTransferencia().getImage()==null)
						|| (x.getTransferencia()!=null && e.getTransferencia()!=null && x.getTransferencia().getImage()!=null && e.getTransferencia().getImage()!=null 
						&& !x.getTransferencia().getImage().getId().equals(e.getTransferencia().getImage().getId())))
					filesToRem.add(x.getTransferencia().getImage());
					
			} catch (Exception e1) {
			}
		}
		
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
		
		TResult<SiteSMDoacao> res = super.save(token, e);
		
		if(!filesToRem.isEmpty()) {
			for(TFileEntity f : filesToRem)
				fileServ.remove(token, f);
		}
		
		return res;
	}
}
