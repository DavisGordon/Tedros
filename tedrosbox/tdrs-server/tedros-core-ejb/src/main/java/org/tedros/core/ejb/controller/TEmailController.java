package org.tedros.core.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.tedros.common.model.TFileEntity;
import org.tedros.core.cdi.bo.TEmailBO;
import org.tedros.core.controller.ITEmailController;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessToken;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.util.TSentEmailException;

@TSecurityInterceptor
@Stateless(name="ITEmailController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TEmailController implements ITEmailController, ITSecurity {

	@Inject
	private TEmailBO bo;
	
	@EJB
	private ITSecurityController securityController;
	
	/**
	 * @return the securityController
	 */
	public ITSecurityController getSecurityController() {
		return securityController;
	}

	@Override
	public TResult<Boolean> send(TAccessToken token, String to, String subject, String content, boolean html) {
		try {
			bo.send(false, to, subject, content, html);
			return new TResult<>(TState.SUCCESS, true);
		}catch(Exception | TSentEmailException e) {
			e.printStackTrace();
			return new TResult<>(TState.ERROR, false, e.getMessage());
		}
	}

	@Override
	public TResult<Boolean> send(TAccessToken token, String to, String subject, String content, boolean html,
			TFileEntity file) {
		try {
			bo.send(false, to, subject, content, html, file);
			return new TResult<>(TState.SUCCESS, true);
		}catch(Exception | TSentEmailException e) {
			e.printStackTrace();
			return new TResult<>(TState.ERROR, false, e.getMessage());
		}
	}
}
