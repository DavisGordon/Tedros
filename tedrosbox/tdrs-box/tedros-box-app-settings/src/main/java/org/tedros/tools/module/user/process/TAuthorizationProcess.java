/**
 * 
 */
package org.tedros.tools.module.user.process;

import java.util.ArrayList;
import java.util.List;

import org.tedros.core.annotation.security.TSecurity;
import org.tedros.core.context.TReflections;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.controller.TAuthorizationController;
import org.tedros.core.security.model.TAuthorization;
import org.tedros.core.security.model.TUser;
import org.tedros.core.service.remote.ServiceLocator;
import org.tedros.fx.exception.TProcessException;
import org.tedros.fx.process.TEntityProcess;
import org.tedros.server.result.TResult;
import org.tedros.tools.module.user.action.TAuthorizationLoadAction;

/**
 * @author Davis Gordon
 *
 */
public class TAuthorizationProcess extends TEntityProcess<TAuthorization> {

	public TAuthorizationProcess() throws TProcessException {
		super(TAuthorization.class, TAuthorizationController.JNDI_NAME);
	}
	
	private List<TAuthorization> lst = null;
	/*
	public void process(List<TAuthorization> lst) {
		this.lst = lst;
	}*/
	
	public void savePolicies() {
		lst = new ArrayList<>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean runBefore(List<TResult<TAuthorization>> res) {
		if(lst!=null){
			
			TReflections.createAppPackagesIndex();
			lst = TAuthorizationLoadAction.getAppsSecurityPolicie( 
					TReflections.getInstance()
					.loadPackages()
					.getClassesAnnotatedWith(TSecurity.class));
			
			ServiceLocator loc = ServiceLocator.getInstance();
			try {
				TUser user = TedrosContext.getLoggedUser();
				TAuthorizationController serv = loc.lookup(TAuthorizationController.JNDI_NAME);
				TResult<TAuthorization> r = serv.process(user.getAccessToken(), lst);
				res.add(r);
			} catch (Exception e) {
				super.buildExceptionResult(res, e);
			}finally {
				loc.close();
			}
		}
		return false;
	}

}
