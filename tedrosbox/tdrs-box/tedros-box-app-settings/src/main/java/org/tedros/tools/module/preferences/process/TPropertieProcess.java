/**
 * 
 */
package org.tedros.tools.module.preferences.process;

import java.util.List;

import org.tedros.common.model.TFileEntity;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.controller.TPropertieController;
import org.tedros.core.security.model.TUser;
import org.tedros.core.service.remote.ServiceLocator;
import org.tedros.core.setting.model.TPropertie;
import org.tedros.fx.exception.TProcessException;
import org.tedros.fx.process.TEntityProcess;
import org.tedros.server.result.TResult;

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
