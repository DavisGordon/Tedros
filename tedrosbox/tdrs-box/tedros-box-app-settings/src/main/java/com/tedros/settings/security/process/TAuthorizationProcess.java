/**
 * 
 */
package com.tedros.settings.security.process;

import java.util.List;

import com.tedros.core.context.TedrosContext;
import com.tedros.core.ejb.controller.TAuthorizationController;
import com.tedros.core.security.model.TAuthorization;
import com.tedros.core.security.model.TUser;
import com.tedros.core.service.remote.ServiceLocator;
import com.tedros.ejb.base.result.TResult;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TEntityProcess;

/**
 * @author Davis Gordon
 *
 */
public class TAuthorizationProcess extends TEntityProcess<TAuthorization> {

	public TAuthorizationProcess() throws TProcessException {
		super(TAuthorization.class, TAuthorizationController.JNDI_NAME);
	}
	
	private List<TAuthorization> lst = null;
	
	public void process(List<TAuthorization> lst) {
		this.lst = lst;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean runBefore(List<TResult<TAuthorization>> res) {
		if(lst!=null){
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
