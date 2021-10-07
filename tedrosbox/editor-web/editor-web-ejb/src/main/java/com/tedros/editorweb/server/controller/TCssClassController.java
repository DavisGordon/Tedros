/**
 * 
 */
package com.tedros.editorweb.server.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.tedros.editorweb.model.CssClass;
import com.tedros.editorweb.server.bo.ITRunService;
import com.tedros.editorweb.server.service.TEntityService;
import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.controller.TSecureEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.security.ITSecurity;
import com.tedros.ejb.base.security.TAccessToken;
import com.tedros.ejb.base.security.TRemoteSecurity;
import com.tedros.ejb.base.service.ITEjbService;

/**
 * @author Davis Gordon
 *
 */
@TRemoteSecurity
@Stateless(name="ITCssClassController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TCssClassController extends TSecureEjbController<CssClass> implements ITCssClassController, ITSecurity{

	@EJB
	private TEntityService<CssClass> serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	protected ITEjbService<CssClass> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return this.securityController;
	}
	/*
	@Override
	public TResult<CssClass> save(TAccessToken token, CssClass e) {
		try {
			((ITRunService<CssClass>) serv).runTx(s -> {
				try {
					s.save(e);
				} catch (Exception e1) {
					throw new RuntimeException(e1);
				}
			});
			return super.findById(token, e);
		}catch(Exception e1) {
			return super.processException(token, e, e1);
		}
		
	}*/


}
