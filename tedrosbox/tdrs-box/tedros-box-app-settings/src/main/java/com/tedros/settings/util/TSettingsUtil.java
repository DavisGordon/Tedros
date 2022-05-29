/**
 * 
 */
package com.tedros.settings.util;

import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;

import com.tedros.core.TLanguage;
import com.tedros.core.context.TedrosContext;
import com.tedros.core.ejb.controller.TOwnerController;
import com.tedros.core.owner.model.TOwner;
import com.tedros.core.service.remote.ServiceLocator;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.TState;
import com.tedros.fxapi.exception.TException;

/**
 * @author Davis Gordon
 *
 */
public final class TSettingsUtil {

	public TSettingsUtil() {

	}
	
	public TOwner getOwner() throws TException {
		ServiceLocator loc = ServiceLocator.getInstance();
		TOwner e = null;
		try {
			TOwnerController serv = loc.lookup(TOwnerController.JNDI_NAME);
			TResult<TOwner> res = serv.getOwner(TedrosContext.getLoggedUser().getAccessToken());
			if(res.getState().equals(TState.SUCCESS)) {
				e = res.getValue();
			}else {
				String msg = res.getMessage();
				if(StringUtils.isBlank(msg))
					msg = TLanguage.getInstance().getString("#{tedros.fxapi.message.error}");
				
				throw new TException(msg);
			}
			
		} catch (NamingException ex) {
			throw new TException(ex.getMessage());
		}finally{
			loc.close();
		}
		
		return e;
	}

}
