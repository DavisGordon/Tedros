/**
 * 
 */
package org.tedros.login.model;

import java.net.ConnectException;
import java.rmi.RemoteException;
import java.util.List;

import org.tedros.core.annotation.security.TSecurity;
import org.tedros.core.context.TReflections;
import org.tedros.core.controller.ITLoginController;
import org.tedros.core.security.model.TAuthorization;
import org.tedros.core.security.model.TUser;
import org.tedros.core.service.remote.ServiceLocator;
import org.tedros.fx.exception.TProcessException;
import org.tedros.fx.process.TEntityProcess;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;

import org.tedros.tools.module.user.action.TAuthorizationLoadAction;

/**
 * @author Davis Gordon
 *
 */
 public class TLoginProcess extends TEntityProcess<TUser> {
	
	private static final String SERV_NAME = "ITLoginControllerRemote";
	private int operation;
	private TUser user;
	
	public TLoginProcess() throws TProcessException {
		super(TUser.class, SERV_NAME);
	} 

	@Override
	public boolean runBefore(List<TResult<TUser>> resultList) {
		ServiceLocator loc = ServiceLocator.getInstance();
		try {
			ITLoginController service = (ITLoginController) loc.lookup(SERV_NAME);
			switch (operation) {
			case 1:
				resultList.add(service.login(user.getLogin(), user.getPassword()));
				break;
			case 2:
				List<TAuthorization> lst= TAuthorizationLoadAction
				.getAppsSecurityPolicie(TReflections.getInstance().getClassesAnnotatedWith(TSecurity.class));
				resultList.add(service.createFirstUser(user, lst));
				break;
			case 3:
				resultList.add(service.saveActiveProfile(user.getAccessToken(), user.getActiveProfile(), user.getId()));
				 break;
			case 4:
				TResult<Boolean> f = service.isSystemWithoutUsers();
				TState e = f.getValue() ? TState.SUCCESS : TState.WARNING;
				TResult<TUser> res = new TResult<>(e);
				resultList.add(res);
				 break;
			}
		} catch (Exception e) {
			String msg = (e.getCause() instanceof ConnectException 
					|| e.getCause() instanceof RemoteException)
					? "SERVER_FAIL"
					: e.getCause().getMessage();
			TResult<TUser> res = new TResult<>(TState.ERROR, msg);
			resultList.add(res);
			LOGGER.error(msg, e);
		}finally {
			loc.close();
		}
		
		return false;
	}
	
	public void login(TUser user) {
		this.user = user;
		this.operation = 1;
	}
	
	public void firstUser(TUser user) {
		this.user = user;
		this.operation = 2;
	}
	
	public void setActiveProfile(TUser user) {
		this.user = user;
		this.operation = 3;
	}
	
	public void isSystemWithoutUsers() {
		this.user = null;
		this.operation = 4;
	}
	
	

}
