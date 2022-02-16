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
import javax.inject.Inject;

import org.somos.ejb.controller.ISiteParceriaController;
import org.somos.model.SiteParceria;
import org.somos.server.acao.bo.EmailBO;
import org.somos.server.base.service.TStatelessService;
import org.somos.server.exception.EmailBusinessException;

import com.tedros.common.model.TFileEntity;
import com.tedros.core.ejb.controller.TFileEntityController;
import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.controller.TSecureEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.ejb.base.security.ITSecurity;
import com.tedros.ejb.base.security.TAccessToken;
import com.tedros.ejb.base.security.TRemoteSecurity;
import com.tedros.ejb.base.service.ITEjbService;
import com.tedros.util.TSentEmailException;

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
	
	@Inject
	private EmailBO emailBo;
	
	@EJB
	private TStatelessService<SiteParceria> serv;
	
	@EJB
	private TFileEntityController fileServ; 

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
		
		List<TFileEntity> filesToRem = new ArrayList<>();
		if(!e.isNew()) {
			try {
				SiteParceria  x = serv.findById(e);
				if((x.getImage()!=null && e.getImage()==null) ||
						(x.getImage()!=null && e.getImage()!=null && !x.getImage().getId().equals(e.getImage().getId())))
					filesToRem.add(x.getImage());
			} catch (Exception e1) {
			}
		}
		
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
		
		TResult<SiteParceria> res = super.save(token, e);
		
		if(!filesToRem.isEmpty()) {
			for(TFileEntity f : filesToRem)
				fileServ.remove(token, f);
		}
		
		return res;
	}

	@Override
	public TResult<String> enviarEmail(TAccessToken token, String empresa, String nome, String contato,
			String tipoAjuda, String descricao, String endereco) {
		
		try {
			emailBo.enviarEmailPropostaAjuda(empresa, nome, contato, tipoAjuda, descricao, endereco);
			return new TResult<>(EnumResult.SUCESS);
		} catch (EmailBusinessException | TSentEmailException e) {
			e.printStackTrace();
			return new TResult<>(EnumResult.ERROR);
		}
		
	}
}
