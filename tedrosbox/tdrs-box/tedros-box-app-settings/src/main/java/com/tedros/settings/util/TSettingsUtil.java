/**
 * 
 */
package com.tedros.settings.util;

import java.util.List;

import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;

import com.tedros.core.TLanguage;
import com.tedros.core.context.TedrosContext;
import com.tedros.core.ejb.controller.TOwnerController;
import com.tedros.core.owner.model.TOwner;
import com.tedros.core.service.remote.ServiceLocator;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.fxapi.exception.TException;
import com.tedros.fxapi.modal.TMessage;
import com.tedros.fxapi.modal.TMessageType;
import com.tedros.settings.security.model.TOwnerMV;

/**
 * @author Davis Gordon
 *
 */
public final class TSettingsUtil {

	public TSettingsUtil() {

	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TOwner getOwner() throws TException {
		ServiceLocator loc = ServiceLocator.getInstance();
		try {
			TOwnerController serv = loc.lookup(TOwnerController.JNDI_NAME);
			TResult res = serv.listAll(TedrosContext.getLoggedUser().getAccessToken(), TOwner.class);
			if(res.getResult().equals(EnumResult.SUCESS)) {
				List<TOwner> l = (List<TOwner>) res.getValue();
				if(l!=null && !l.isEmpty()) {
					TOwner e = l.get(0);
					return e;
				}else
					return null;
			}else {
				String msg = res.getMessage();
				if(StringUtils.isBlank(msg))
					msg = TLanguage.getInstance().getString("#{tedros.fxapi.message.error}");
				
				throw new TException(msg);
			}
			
		} catch (NamingException e) {
			throw new TException(e.getMessage());
		}finally{
			loc.close();
		}
	}

}
