/**
 * 
 */
package com.tedros.settings.properties.process;

import java.util.List;

import com.tedros.common.model.TFileEntity;
import com.tedros.core.context.TedrosContext;
import com.tedros.core.ejb.controller.TPropertieController;
import com.tedros.core.security.model.TUser;
import com.tedros.core.service.remote.ServiceLocator;
import com.tedros.core.setting.model.TPropertie;
import com.tedros.ejb.base.result.TResult;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TEntityProcess;

/**
 * @author Davis Gordon
 *
 */
public class TPropertieProcess extends TEntityProcess<TPropertie> {

	public TPropertieProcess() throws TProcessException {
		super(TPropertie.class, TPropertieController.JNDI_NAME);
	}
	
	private String valueKey = null;
	private String fileKey = null;
	
	public void valueFromKey(String key) {
		this.valueKey = key;
	}

	public void fileFromKey(String key) {
		this.fileKey = key;
	}

	@Override
	public boolean runBefore(List<TResult<TPropertie>> res) {
		if(valueKey!=null){
			ServiceLocator loc = ServiceLocator.getInstance();
			try {
				TUser user = TedrosContext.getLoggedUser();
				TPropertieController serv = loc.lookup(TPropertieController.JNDI_NAME);
				TResult<String> r = serv.getValue(user.getAccessToken(), valueKey);
				TPropertie p = new TPropertie();
				p.setKey(valueKey);
				p.setValue(r.getValue());
				res.add(new TResult<>(r.getState(), p));
			} catch (Exception e) {
				super.buildExceptionResult(res, e);
			}finally {
				loc.close();
			}
			return false;
		}else
		if(fileKey!=null){
			ServiceLocator loc = ServiceLocator.getInstance();
			try {
				TUser user = TedrosContext.getLoggedUser();
				TPropertieController serv = loc.lookup(TPropertieController.JNDI_NAME);
				TResult<TFileEntity> r = serv.getFile(user.getAccessToken(), fileKey);
				TPropertie p = new TPropertie();
				p.setKey(valueKey);
				p.setFile(r.getValue());
				res.add(new TResult<>(r.getState(), p));
			} catch (Exception e) {
				super.buildExceptionResult(res, e);
			}finally {
				loc.close();
			}
			return false;
		}
		
		return true;
	}

}
