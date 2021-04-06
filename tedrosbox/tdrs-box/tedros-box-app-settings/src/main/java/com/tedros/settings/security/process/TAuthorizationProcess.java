/**
 * 
 */
package com.tedros.settings.security.process;

import java.util.List;

import javax.naming.NamingException;

import com.tedros.core.ejb.controller.TAuthorizationController;
import com.tedros.core.security.model.TAuthorization;
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
		super(TAuthorization.class, "TAuthorizationControllerRemote");
	}
	
	private List<TAuthorization> lst = null;
	
	public void process(List<TAuthorization> lst) {
		this.lst = lst;
	}

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.process.TEntityProcess#execute(java.util.List)
	 */
	@Override
	public void execute(List<TResult<TAuthorization>> res) {
		if(lst==null)
			return;
		ServiceLocator loc = ServiceLocator.getInstance();
		try {
			TAuthorizationController serv = loc.lookup("TAuthorizationControllerRemote");
			res.add(serv.process(lst));
		} catch (NamingException e) {
			e.printStackTrace();
		}finally {
			loc.close();
		}
	}

}
